package com.digitalchina.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.MscxOrderServiceApplication;
import com.digitalchina.common.service.IOrderService;
import com.digitalchina.common.service.IPayLogService;
import com.digitalchina.common.service.IRouterRuleSeivice;
import com.digitalchina.common.service.Impl.ApiServiceImpl;
import com.digitalchina.common.service.Impl.OrderDetailServiceImpl;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.common.util.HttpClientUtil;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.dao.BookDao;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.domain.Book;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.OrderInfo;
import com.digitalchina.domain.RouterRule;
import com.digitalchina.dto.IsFreePurchaseDto;
import com.digitalchina.dto.PaySendDto;
import com.digitalchina.dto.SourceIdCountDto;
import com.digitalchina.exception.PlaceOrderException;
import com.digitalchina.invoketrace.client.InvokeTrace;
import com.digitalchina.resttemplate.httpclient.RestTemplateWithHttpClientUtil;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by Snail on 2016/12/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MscxOrderServiceApplication.class)
@WebAppConfiguration

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
public class OrderServiceTest {
    @Autowired
    IRouterRuleSeivice iRouterRuleSeivice;

    @Autowired
    ApiServiceImpl apiService;

    @Autowired
    IPayLogService iPayLogService;
    @Autowired
    IOrderService iOrderService;
    @Autowired
    OrderDetailServiceImpl orderDetailService;

    @Autowired
    CallUrlConfig callUrlConfig;
    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;

    @Autowired
    RestTemplateWithHttpClientUtil httpClientUtil;


    private static class GatewayRequestCallback implements RequestCallback, InvokeTrace {

        private String params;

        private HttpHeaders headers;

        public GatewayRequestCallback(){}

        public GatewayRequestCallback(String params, HttpHeaders headers){
            this.params = params;
            this.headers = headers;
        }

        @Override
        public byte[] getBytes() {
            return this.params == null ? null : this.params.getBytes();
        }

        @Override
        public HttpHeaders getHeaders() {
            return this.headers;
        }

        @Override
        public void doWithRequest(ClientHttpRequest request) throws IOException {
            String params = this.params;
            HttpHeaders headers = request.getHeaders();
            if(getHeaders() != null){
                Set<String> keys = this.getHeaders().keySet();
                for (String key : keys) {
                    headers.put(key, this.getHeaders().get(key));
                }
            }
            //need encoded?
			/*if(headers.getContentType() == MediaType.APPLICATION_FORM_URLENCODED){
				params = params == null ? "" :  URLEncoder.encode(this.params, "UTF-8");
			}*/
            OutputStream out = request.getBody();
            out.write(params.getBytes());
            out.flush();
            out.close();
        }

        public String getParams() {
            return params;
        }

        public void setParams(String params) {
            this.params = params;
        }

        public void setHeaders(HttpHeaders headers) {
            this.headers = headers;
        }

    }

    /**
     * 北京网关 返回数据解析
     * @author chenfq
     *
     * @param <Object>
     */
    @SuppressWarnings("hiding")
    private static class GatewayResponseExecutor<Object> implements ResponseExtractor<Object> {

        public GatewayResponseExecutor(){

        }

        @SuppressWarnings("unchecked")
        @Override
        public Object extractData(ClientHttpResponse response) throws IOException {
            InputStream input = response.getBody();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte  []bytes = new byte[1024];
            int read = -1;
            while((read = input.read(bytes)) >0){
                out.write(bytes,0,read);
            }
            return (Object) out.toString("UTF-8");
        }

    }




    @Test
    public void findRule(){
        Map<String, Object> confMap = new HashMap<>();
        confMap.put("userId", "440100000002383");
        confMap.put("sourceId", "10073");
        confMap.put("nowDate", new Date());
        confMap.put("sourceType", ChangeConstants.WAI_APP_TYPE);
        IsFreePurchaseDto isFreePurchaseDto=iRouterRuleSeivice.getIfFreeDto(confMap);
        System.out.print(isFreePurchaseDto);
    }

    @Test
    public void insertDb() {

        //根据订单号去查询资
        List<RouterRule> routerRules = iRouterRuleSeivice.getRouterRuleByOrderNum("440100484639392973");
        for(RouterRule rr:routerRules){
            OrderDetail orderDetail = orderDetailService.findOrderDetailByDetailId(rr.getOrderDetailId());
            iRouterRuleSeivice.pushRule(rr,orderDetail);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8"));
            Map<String,Object> params = new HashMap<>();
            Map<String,Object> sourceMap = new HashMap<>();
            sourceMap.put("userId",rr.getOrderUserId());
            sourceMap.put("resourceId",rr.getSourceId());
            sourceMap.put("resourceType",orderDetail.getResourceType());
            JSONObject orderDetailParse = (JSONObject) JSONObject.parse( orderDetail.getSourceJson());
            if(ChangeConstants.DATA_TYPE.equals(orderDetail.getResourceType())){
                sourceMap.put("charRuleType","-1");
            }
            if(orderDetail.getItemCash()==0){
                sourceMap.put("charRuleType","0");
            }if(ChangeConstants.CHARGE_TYPE_BY_FREQUENCY.equals(orderDetailParse.getString("chargeMethod"))){
                sourceMap.put("charRuleType","2");
            }if(ChangeConstants.CHARGE_TYPE_BY_TIME.equals(orderDetailParse.getString("chargeMethod"))){
                sourceMap.put("charRuleType","1");
            }
            sourceMap.put("startTime", DateUtil.format(rr.getEffectiveTime()));
            sourceMap.put("endTime",DateUtil.format(rr.getIneffectiveTime()));
            sourceMap.put("number",rr.getEffectiveNumber());
            sourceMap.put("orderDetailId",orderDetail.getId());
            List<Map> paramsList = new ArrayList<>();
            paramsList.add(sourceMap);
            params.put("data",paramsList);
            String paramsT = "data="+JSON.toJSONString(paramsList);
//            String response = (String) restTemplateUtil.execute("mscx-gateway.hanlnk.com:82", "/gateway-web-1.8.0/addRule.do", HttpMethod.POST, new GatewayRequestCallback(paramsT,headers), new GatewayResponseExecutor());
            String response = (String) restTemplateUtil.execute("gateway","/gateway-web-1.8.0/addRule.do",HttpMethod.POST, new GatewayRequestCallback(paramsT,headers), new GatewayResponseExecutor());

            JSONArray array = (JSONArray) JSONObject.parse(response);
            array.getJSONObject(0).getString("result");

//            JSONObject temp = array.getJSONObject(i);

            JSONObject parse = (JSONObject) JSONObject.parse(response);
            if(parse.getString("code").equals(ChangeConstants.ERROR_CODE)) throw new PlaceOrderException(ChangeConstants.ERROR_CODE,parse.getString("message"));






        }

//        List<RouterRule> routerRules= iRouterRuleSeivice.getRouterRuleByOrderNum("440100482490789424");
//        if(routerRules!=null && routerRules.size()>0){
//            for (RouterRule rr : routerRules){
//                iRouterRuleSeivice.updateStatus(rr.getId(), ChangeConstants.SOURCE_ROUTER_PAY_VERIFY);
//            }
//        }
//        Map<Integer, Integer> dataMap = new HashMap<>();
//        Map<Integer, Integer> apiMap = new HashMap<>();
//        List<SourceIdCountDto> sourceIdCountDtos= apiService.getCountSource();
//        for (SourceIdCountDto ss : sourceIdCountDtos) {
//            if (ChangeConstants.DATA_TYPE.equals(ss.getResourceType())) {
//                dataMap.put(ss.getResourceId(), ss.getSourceNum());
//            } else {
//                apiMap.put(ss.getResourceId(), ss.getSourceNum());
//            }
//        }
//        System.out.print(apiMap);
//        OrderInfo orderInfo = new OrderInfo();
//        OrderInfo orderInfo = iOrderService.getOrderInfoByOrderNum("440100483414666603");
////        orderInfo.setOrderNum("123455666");
//        iPayLogService.addPayLog(orderInfo);
//        List<SourceIdCountDto> sourceIdCountDtos = iOrderService.getCountSource();
//        List<Map> apply = new ArrayList<>();
//        for (SourceIdCountDto ss : sourceIdCountDtos) {
//            if (ChangeConstants.API_TYPE.equals(ss.getResourceType())) {
//                Map<String, Integer> apiMap = new HashMap<>();
//                apiMap.put("sourceId", ss.getResourceId());
//                apiMap.put("userNum", ss.getUserNum());
//                apiMap.put("applyNum", ss.getApplyNum());
//                apply.add(apiMap);
//            }
////                    else {
////                        apiMap.put(ss.getResourceId(), ss.getSourceNum());
////                    }
//        }
//        System.out.println("**************************************推送统计信息****************************************************");
//        String response =restTemplateUtil.post(callUrlConfig.getApiHost(),callUrlConfig.getApiStatisticsUrl(),apply,null);
//        System.out.println(response);
    }

}
//
//    @Autowired
//    IOrderServiceImpl iOrderService;
//
//
////    @Autowired
////    private MockMvc mvc;
////
////    String orderJson = "[{'userId':'1','area':'111111','resouce_id':4,'resource_type':null,'resouece_name':'tiansqiApi','chare_rule_id':2,'charge_rule_name':'套餐一','charge_rule_des':'sssss','charge_rule_type':0,'item_cash':9,'item_number':4,'order_type':'online'},{'userId':'2','area':null,'resouce_id':4,'resource_type':null,'resouece_name':'tiansqiApi','chare_rule_id':2,'charge_rule_name':'套餐一','charge_rule_des':'sssss','charge_rule_type':1,'item_cash':9.2,'item_number':4,'order_type':'online'}]";
////    @Test
////    public void getDataApiCatalogTest() throws Exception {
////        mvc.perform(MockMvcRequestBuilders.post("/orderT/placeOrder.do").param("orderJson",orderJson));
////    }
//
//    @Test
//    public void insertDb() {
//
//        PaySendDto pdto = new PaySendDto();
//        pdto.setAppId("");
//        pdto.setChannel("");
//        pdto.setReturnUrl("/payOrderBack");
//        pdto.setTitle("test");
//        // pdto.setQrPayMode("");
//        //  pdto.setBillTimeout(4);
//
//        String postJson = JSON.toJSONString(pdto);
//
//
//}
//
//
//    }
//
