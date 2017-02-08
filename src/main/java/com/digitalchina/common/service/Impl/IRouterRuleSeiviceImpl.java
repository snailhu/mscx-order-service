package com.digitalchina.common.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.GatewayRequestCallback;
import com.digitalchina.common.GatewayResponseExecutor;
import com.digitalchina.common.service.IRouterRuleSeivice;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.common.util.HttpClientUtil;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.dao.RouterRuleMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.OrderInfo;
import com.digitalchina.domain.RouterRule;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;
import com.digitalchina.exception.PlaceOrderException;
import com.digitalchina.resttemplate.httpclient.RestTemplateWithHttpClientUtil;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import org.codehaus.groovy.runtime.metaclass.ConcurrentReaderHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Snail on 2016/12/2.
 */
@Service
public class IRouterRuleSeiviceImpl implements IRouterRuleSeivice{
    @Autowired
    RouterRuleMapper routerRuleMapper;

    @Autowired
    ISourceServieImpl iSourceServie;

    @Autowired
    ApiServiceImpl apiService;
    @Autowired
    CallUrlConfig callUrlConfig;
    @Autowired
    RestTemplateWithHttpClientUtil httpClientUtil;

    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;

    @Autowired
    IApiRouterRuleService iApiRouterRuleService;

    @Autowired
    IDataRouterRuleService iDataRouterRuleService;
    @Autowired
    IAppRouterRuleService iAppRouterRuleService;

    @Override
    @Transactional
    public void doRouterRule(OrderDetail orderDetail,SourceDto sdto,PackageDto pdto,WeiAppDto wdto,UserData userData,int orderDetailId) {
        boolean isFree=true;
        if(sdto!=null)isFree=sdto.isFree();
        if(pdto!=null)isFree=pdto.isFree();
        if(wdto!=null)isFree=wdto.isFree();
        //判断是否免费
        if(isFree){
            RouterRule rr = new RouterRule();
            //创建路由规则
           // if(pdto==null){ //如果 套餐为空 为数据资源
                //免费数据
                Calendar rightNow = Calendar.getInstance();
                Date startDate = rightNow.getTime();
                rightNow.add(Calendar.YEAR,ChangeConstants.DEFAULT_USR_TIME_YEAR);
                Date endDate =rightNow.getTime();
                //新建规则
                if(sdto!=null){
                    rr.setArea(sdto.getArea());
                    rr.setSourceId(sdto.getSourceId());
                }
                if(pdto!=null){
                    rr.setArea(pdto.getArea());
                    rr.setSourceId(pdto.getSourceId());
                }
                if(wdto!=null){
                    rr.setArea(wdto.getArea());
                    rr.setSourceId(wdto.getSourceId());
                }

                rr.setOrderDetailId(orderDetailId);
                rr.setCreateUserId(userData.getUserId());
                rr.setEffectiveTime(startDate);
                rr.setIneffectiveTime(endDate);
                rr.setOrderUserId(userData.getUserId());
                rr.setSourceType(orderDetail.getResourceType());
                rr.setEffectiveNumber(ChangeConstants.NO_FREQUENCY_LIMIT);
                rr.setSourceStatus(ChangeConstants.SOURCE_ROUTER_PAY_VERIFY);
                rr.setOrderNum(userData.getTemNum());
                routerRuleMapper.insert(rr);
                pushRule(rr,orderDetail);
                apiService.doPushStatic();
          //  }
//            else{
//                Date startDate=null;
//                Date endDate=null;
//                int effective_number=-1;
//                //按时间
//                if(pdto.getDealyTime()!=null){
//                    Calendar rightNow = Calendar.getInstance();
//                     startDate = rightNow.getTime();
//                    rightNow.add(Calendar.YEAR,ChangeConstants.DEFAULT_USR_TIME_YEAR);
//                    endDate =rightNow.getTime();
//
//                    if(orderDetail.getResourceDelayTime()!=null){
//                        effective_number =ChangeConstants.NO_FREQUENCY_LIMIT;
//                    }
//                    //int effective_number=orderDetail.getItemNumber()*pdto.getDefaultUseTime();
//                }
//                /**免费套餐按次数不设定期限 **/
//                else if(pdto.getDefaultUseTime()!=0){
//                    Calendar rightNow = Calendar.getInstance();
//                    startDate = rightNow.getTime();
//                    rightNow.add(Calendar.YEAR,ChangeConstants.DEFAULT_USR_TIME);//默认两年
//                    endDate =rightNow.getTime();
//                    effective_number=orderDetail.getItemNumber()*pdto.getDefaultUseTime();
//                }
//                else{
//                    Calendar rightNow = Calendar.getInstance();
//                    startDate = rightNow.getTime();
//                    rightNow.add(Calendar.YEAR,ChangeConstants.DEFAULT_USR_TIME);//默认两年
//                    endDate =rightNow.getTime();
//                }
//                //新建规则
//                rr.setSourceType(ChangeConstants.API_TYPE);
//                rr.setArea(pdto.getArea());
//                rr.setOrderDetailId(orderDetailId);
//                rr.setSourceId(pdto.getSourceId());
//                rr.setCreateUserId(userData.getUserId());
//                rr.setEffectiveTime(startDate);
//                rr.setIneffectiveTime(endDate);
//                rr.setOrderUserId(userData.getUserId());
//                rr.setEffectiveNumber(effective_number);
//                rr.setOrderNum(userData.getTemNum());
//                rr.setSourceStatus(ChangeConstants.SOURCE_ROUTER_PAY_VERIFY);
//            }


        }else{  // 如果不免费
            JSONObject parse = (JSONObject) JSONObject.parse( orderDetail.getSourceJson());
            //套餐收费
              if(pdto!=null){
                  iApiRouterRuleService.doRouterRule(orderDetail,pdto,userData);
            }else if(sdto!=null){  //数据收费
                  iDataRouterRuleService.doRouterRule(orderDetail,sdto,userData);
              }else if(wdto!=null){
                  iAppRouterRuleService.doRouterRule(orderDetail,wdto,userData);
              }
        }

    }

    @Override
    public List<RouterRule> getRouterRuleByDetailId(Integer id) {
        return null;
    }

    @Override
    public int getByAllId(Map<String, Object> confMap) {

        return routerRuleMapper.getByAllId(confMap);
    }

    @Override
    public List<RouterRule> getRouterRuleByOrderNum(String orderNum) {
        return routerRuleMapper.getRouterRuleByOrderNum(orderNum);
    }

    @Override
    public void updateStatus(int id,String status) {
        Map<String, Object> conf =new HashMap<>();
        conf.put("id",id);
        conf.put("status",status);
        routerRuleMapper.updateStatus(conf);
    }

    @Override
    public void pushRule(RouterRule rr,OrderDetail orderDetail){
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Map<String, Object> header = new HashMap<String, Object>();
                Map<String,Object> sourceMap = new HashMap<>();
                sourceMap.put("userId",rr.getOrderUserId());
                sourceMap.put("resourceId",String.valueOf(rr.getSourceId()));
                sourceMap.put("resourceType",orderDetail.getResourceType());
                JSONObject orderDetailParse = (JSONObject) JSONObject.parse( orderDetail.getSourceJson());
//                if(ChangeConstants.DATA_TYPE.equals(orderDetail.getResourceType())){
//                    sourceMap.put("charRuleType","-1");
//                }
                if(orderDetail.isFree()){
                    sourceMap.put("charRuleType","0");

                }else{
                    if(ChangeConstants.DATA_TYPE.equals(orderDetail.getResourceType())){
                        sourceMap.put("charRuleType","1");}
                    if(orderDetail.getItemCash()==0){
                        sourceMap.put("charRuleType","0");
                    }if(ChangeConstants.CHARGE_TYPE_BY_FREQUENCY.equals(orderDetailParse.getString("chargeMethod"))){
                        sourceMap.put("charRuleType","2");
                    }if(ChangeConstants.CHARGE_TYPE_BY_TIME.equals(orderDetailParse.getString("chargeMethod"))){
                        sourceMap.put("charRuleType","1");
                    }
                }
                sourceMap.put("startTime",DateUtil.format(rr.getEffectiveTime()));
                sourceMap.put("endTime",DateUtil.format(rr.getIneffectiveTime()));
                sourceMap.put("number",rr.getEffectiveNumber());
                sourceMap.put("orderDetailId",orderDetail.getId());
                List<Map> paramsList = new ArrayList<>();
                paramsList.add(sourceMap);
                String paramsT = "data="+JSON.toJSONString(paramsList);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8"));
                String response=null;
                try{
                    System.out.print("**********开始推送网关***********888888888");
                     response = (String) restTemplateUtil.execute(callUrlConfig.getGateWayHost(), callUrlConfig.getPushRuleUrl(), HttpMethod.POST, new GatewayRequestCallback(paramsT,headers), new GatewayResponseExecutor());
                    System.out.print("**********推送网关结束*************************");
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.print(response);
              JSONArray array = (JSONArray) JSONObject.parse(response);
                array.getJSONObject(0).getString("result");
            }
        }).start();
    }

    @Override
    public IsFreePurchaseDto getIfFreeDto(Map<String, Object> confMap) {
        IsFreePurchaseDto isFreeDto = routerRuleMapper.getIfFreeDto(confMap);
        return  isFreeDto;
    }
}
