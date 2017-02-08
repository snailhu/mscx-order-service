package com.digitalchina.common.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.GatewayRequestCallback;
import com.digitalchina.common.GatewayResponseExecutor;
import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.common.util.HttpClientUtil;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.config.PackageConfig;
import com.digitalchina.config.RouterConfig;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.dao.OrderInfoMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.OrderInfo;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;
import com.digitalchina.exception.PlaceOrderException;
import com.digitalchina.resttemplate.httpclient.RestTemplateWithHttpClientUtil;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import org.apache.ibatis.ognl.IntHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * Created by Snail on 2016/12/6.
 */
@Service
public class ApiServiceImpl {

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;
    @Autowired
    RouterConfig routerConfig;
    @Autowired
    CallUrlConfig callUrlConfig;
    @Autowired
    PackageConfig packageConfig;

    @Autowired
    ISourceServieImpl iSourceServie;

    @Autowired
    RestTemplateWithHttpClientUtil httpClientUtil;

    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;

    /**
     * 获取用户申请购买的api
     * @param orderDetailConfMap
     * @return
     */
    public List<OrderApiDto>  getOrderDetailByUserIdAndType(Map<String,Object> orderDetailConfMap, UserData userData){
        List<OrderApiDto> orderApiDtos = new ArrayList<>();
       List<OrderDetail> orderDetails =  orderDetailMapper.getOrderDetailByConf(orderDetailConfMap);
        if((orderDetails==null) || (orderDetails.size()==0)) return orderApiDtos;
        List<SourcePackage> sourcePackages = new ArrayList<>();
        Map<String,Object> sourceUse = new HashMap<>();

        List<Map> paramsList = new ArrayList<>();

        List<Integer> sourceIds = new ArrayList<>();

        for(OrderDetail orderDetail:orderDetails){
            Map<String,Object> sourceMap = new HashMap<>();
            sourceMap.put("resourceId",orderDetail.getResourceId());
            sourceMap.put("resourceType",orderDetail.getResourceType());
            sourceMap.put("userId",orderDetail.getOrderUserId());
            paramsList.add(sourceMap);
            sourceIds.add(orderDetail.getResourceId());
        }
       Map<Integer,ViewAndApplyDto>  viewAndApplyDtoMap = iSourceServie.getViewApply(sourceIds,userData);
        Boolean isSuccess =doAllUseNumUrl(sourceUse,paramsList);
        System.out.println(isSuccess+".isSuccess.........................................");
        for(OrderDetail orderDetail:orderDetails){
                OrderApiDto orderApiDto = new OrderApiDto();
                JSONObject parse = (JSONObject) JSONObject.parse( orderDetail.getSourceJson());
                if(orderDetail.isFree()){
                    orderApiDto.setChargeType("免费");
                    orderApiDto.setTypeStatus(parse.getString("chargeType"));
                }else{orderApiDto.setChargeType("收费");
                    orderApiDto.setTypeStatus(parse.getString("chargeMethod"));
                }
                orderApiDto.setApplyTime(DateUtil.format(orderDetail.getCreatedTime()));
                orderApiDto.setLogoUrl(parse.getString("logoUrl"));
                if(isSuccess){
                   Map<String,Object> access_apply = (HashMap<String, Object>) sourceUse.get(orderDetail.getResourceId()+"");
                    if(access_apply!=null){
                        orderApiDto.setTotalTime((Integer) access_apply.get("totalTime"));
                        orderApiDto.setRemainTime((Integer) access_apply.get("remainTime"));
                    }

                }else{
                    orderApiDto.setTotalTime(0);
                    orderApiDto.setRemainTime(0);
                }
                if(viewAndApplyDtoMap!=null){
                    ViewAndApplyDto viewAndApplyDto= viewAndApplyDtoMap.get(orderDetail.getResourceId());
                    if(viewAndApplyDto!=null){
                        orderApiDto.setApplyCnt(viewAndApplyDto.getApplyCnt()+"");
                        orderApiDto.setViewCnt(viewAndApplyDto.getViewCnt()+"");
                        orderApiDto.setStatus(viewAndApplyDto.getStatus()+"");
                    }
                }
                orderApiDto.setApiName(parse.getString("apiName"));
                orderApiDto.setApiDesc(parse.getString("apiServiceDesc"));
                orderApiDto.setChargeCount(parse.getString("chargeCount"));
                orderApiDto.setPrice(parse.getString("price"));
                orderApiDto.setSourceId(orderDetail.getResourceId());
                orderApiDto.setSourcePakcageId(orderDetail.getChargeRuleId());
                orderApiDtos.add(orderApiDto);
        }
        System.out.println(orderApiDtos);
        return  orderApiDtos;
    }

    //获取我的api 总数

    public int getSelfApiNum(Map<String,Object> conf){

      return   orderDetailMapper.getSelfApiCount(conf);
    }

//获取  我申请的 api的总数(daiding)
    public int getOrderApiListCount(String userId, String type){

        Map<String ,Object> confMap = new HashMap<>();
        confMap.put("userId",userId);
        confMap.put("resourceType",type);
        return orderDetailMapper.getOrderListApiCount(confMap);
    }

    //根据用户id 资源id 返回资源的购买次数
    public  Map<String,Object> getApiBuyTimes(ConfPageDto cDto){
        Map<String,Object> apiTimeMap = new HashMap<>();
        for(Object id:cDto.getApiIdList()){
//            List<OrderDetail> orderDetails =orderDetailMapper.getOrderDetailBySourceId((int)id);
            List<OrderDetail> orderDetails =null;
            int buyTime = 0;
            for(OrderDetail orderDetail:orderDetails){
                OrderInfo orderInfo = orderInfoMapper.findByOrderId(orderDetail.getOrderId());
                if(orderInfo.getOrderStatus()== OrderStatusEnum.PAY_SUCCESS.getIndex()){
                    buyTime+=orderDetail.getItemNumber();
                }
            }
            apiTimeMap.put(id+"",buyTime);
        }
        return  apiTimeMap;
    }

    //获取某人api购买的次数

   public  List<SourceIdCountDto> getCountSource(){
        return orderDetailMapper.getCountSource();
    }

    // 推送统计数据
    public void doPushStatic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Map<Integer, Integer> dataMap = new HashMap<>();

                List<SourceIdCountDto> sourceIdCountDtos = orderDetailMapper.getCountSource();
                List<Map> apply = new ArrayList<>();
                List<Map>  appStatistics  = new ArrayList<Map>();
                for (SourceIdCountDto ss : sourceIdCountDtos) {
                        Map<String, Integer> apiMap = new HashMap<>();
                        apiMap.put("sourceId", ss.getResourceId());
                        apiMap.put("userNum", ss.getUserNum());
                        apiMap.put("applyNum", ss.getApplyNum());
                    if (ChangeConstants.API_TYPE.equals(ss.getResourceType())) {
                        apply.add(apiMap);
                    }
                    if(ChangeConstants.WAI_APP_TYPE.equals(ss.getResourceType())){
                        appStatistics.add(apiMap);
                    }
                }
                System.out.println("**************************************推送统计信息****************************************************");
                String apiResponse =restTemplateUtil.post(callUrlConfig.getApiHost(),callUrlConfig.getApiStatisticsUrl(),apply,null);
                System.out.println(apiResponse+"..........................................");
                String appResponse =restTemplateUtil.post(callUrlConfig.getAppHost(),callUrlConfig.getAppStatisticsUrl(),appStatistics,null);
                System.out.println(appResponse+"..........................................");
            }
        }).start();
    }

    //获取资源的有效的剩余次数
    public Boolean doAllUseNumUrl(Map<String,Object> sourceUse,List<Map> paramsList){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8"));
        String paramsT = "data="+JSON.toJSONString(paramsList);
           String response=null;
            try {
                System.out.println(".comming.........................................");
                response = (String) restTemplateUtil.execute(callUrlConfig.getGateWayHost(), callUrlConfig.getAllUseNumUrl(), HttpMethod.POST, new GatewayRequestCallback(paramsT,headers), new GatewayResponseExecutor());
                System.out.println(response+"response,response,response****************************************");
                if(response==null) return false;
                JSONObject parseResponse = (JSONObject) JSONObject.parse(response);
                if(parseResponse.getString("code").equals(ChangeConstants.ERROR_CODE)) throw new PlaceOrderException(ChangeConstants.ERROR_CODE,parseResponse.getString("message"));
                JSONArray array = parseResponse.getJSONArray("result");
                if(array==null||array.size()==0)throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"没有获取该资源的使用次数");
                for(int i=0;i<array.size();i++){
                    JSONObject temp = array.getJSONObject(i);
                    Map<String,Object> single =  new HashMap<>();
                    single.put("totalTime",temp.getIntValue("Unused"));
                    single.put("remainTime",temp.getIntValue("used"));
                    sourceUse.put(temp.getString("resourceId"),single);
                }
                return true;
            }catch (Exception e){
                System.out.println(".error comming.........................................");
                e.printStackTrace();
                return false;
            }
    }


    private String getParameters(List<Integer> ids){
        StringBuilder param = new StringBuilder();
        for(Integer temp : ids){
            param.append(",").append(temp);
        }
        return param.toString().substring(1);
    }
}
