package com.digitalchina.common.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.GatewayRequestCallback;
import com.digitalchina.common.GatewayResponseExecutor;
import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.RtnData;
import com.digitalchina.common.service.IFreePayService;
import com.digitalchina.common.service.IPayService;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.common.util.HttpHeaderInfoUtil;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.dao.IdGeneratorDao;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.dao.OrderInfoMapper;
import com.digitalchina.dao.RouterRuleMapper;
import com.digitalchina.domain.OrderInfo;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;
import com.digitalchina.exception.PlaceOrderException;
import com.digitalchina.resttemplate.httpclient.RestTemplateWithHttpClientUtil;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Snail on 2016/12/15.
 */
@Service
public class DoFreeOrderServiceImpl {

    @Autowired
    IdGeneratorDao idGeneratorDao;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private IFreePayService iFreePayService;

    @Autowired
    private IPayService payService;
    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    UserService userService;

    @Autowired
    RouterRuleMapper routerRuleMapper;

    @Autowired
    CallUrlConfig callUrlConfig;
    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;



    /**
     * 免费数据下单
     * @param odto
     * @param packageDtoMap
     * @param sourceDtoMap
     * @param userData
     * @return
     */
    @Transactional
    void doFreeOrder(OmitOrderDto odto,Map<String,PackageDto> packageDtoMap,Map<Integer,SourceDto> sourceDtoMap,Map<String, WeiAppDto> weiAppDtoMap,UserData userData){
        //创建订单
        String name = userData.getAccount();
        String userId = userData.getUserId();
        Date orderDate = new Date();
        OrderInfo orderInfo = new OrderInfo();
        String orderNum =userData.getTemNum();
        orderInfo.setArea(userData.getArea());
        orderInfo.setOrderNum(orderNum);
        orderInfo.setUserId(userId);
        orderInfo.setOrderTime(orderDate);
        orderInfo.setCreatedTime(orderDate);
        orderInfo.setOrderUserName(name);
        orderInfo.setUpdatedBy(userData.getAccount());
        orderInfo.setUdpatedTime(orderDate);
        orderInfo.setOrderType("online");
        double total_cash=0.0;
        orderInfo.setOrderStatus(OrderStatusEnum.PAY_SUCCESS.getIndex());
        orderInfo.setOrderCashTotal(total_cash);
        orderInfoMapper.insert(orderInfo);
        int id  = orderInfo.getId();
            for(OmitSourceDto osdto :odto.getSourceList()) {
                PackageDto pdto = null;
                SourceDto sdto = null;
                WeiAppDto wdto=null;
                //如果为api资源
                Map<String, Object> confMap = new HashMap<>();
                confMap.put("userId", userData.getUserId());
                confMap.put("sourceId", osdto.getSourceId());
                confMap.put("nowDate", new Date());
                if (osdto.getSourceType().equals(ChangeConstants.API_TYPE)) {
                    pdto = packageDtoMap.get(osdto.getSourceId() + "" + osdto.getChar_rule_id());
                    confMap.put("sourceType", ChangeConstants.API_TYPE);
                    if(!(pdto.getStatus().equals("0"))) throw new PlaceOrderException(ChangeConstants.ERROR_CODE,pdto.getPackageName()+"该资源已经下架");
                } else if (osdto.getSourceType().equals(ChangeConstants.DATA_TYPE)) { //数据资源
                    sdto = sourceDtoMap.get(osdto.getSourceId());
                    confMap.put("sourceType", ChangeConstants.DATA_TYPE);
                    if(!(sdto.getStatus().equals("0"))) throw new PlaceOrderException(ChangeConstants.ERROR_CODE,sdto.getSourceName()+"该资源已经下架");
                }else if(osdto.getSourceType().equals(ChangeConstants.WAI_APP_TYPE)){
                    wdto = weiAppDtoMap.get(osdto.getSourceId() + "" + osdto.getChar_rule_id());
                    confMap.put("sourceType", ChangeConstants.WAI_APP_TYPE);
                    if(!(wdto.getStatus().equals("0"))) throw new PlaceOrderException(ChangeConstants.ERROR_CODE,pdto.getPackageName()+"该资源已经下架");
                    if(ChangeConstants.CHARGE_STATUS_OUTLINE.equals(wdto.getChargeStatus())){
                        throw new PlaceOrderException(ChangeConstants.ERROR_CODE,pdto.getPackageName()+"该资源已经下架");
                    }
                }
                IsFreePurchaseDto isFreeDto = routerRuleMapper.getIfFreeDto(confMap);
                if(isFreeDto==null || isFreeDto.getCountNum()==0){
                    iFreePayService.doDetailOrder(osdto,orderNum,sdto,pdto,wdto,userData,id);
                }else if((isFreeDto.getCountNum()>0) && (isFreeDto.getEffectiveNum()<0)){
                   throw new PlaceOrderException("该资源已经订购");
                }else if((isFreeDto.getCountNum()>0) && (isFreeDto.getEffectiveNum()>0)){
                        //向网关请求当前资源的可以使用的剩余次数
//                    headers.setContentType(MediaType.parseMediaType("multipart/x-www-form-urlendcode; charset=UTF-8"));
                        Map<String,Object> params = new HashMap<>();
                        Map<String,Object> sourceMap = new HashMap<>();
                        sourceMap.put("resourceId",osdto.getSourceId());
                        sourceMap.put("resourceType",osdto.getSourceType());
                        sourceMap.put("timePoint",DateUtil.format(new Date()));
                        sourceMap.put("userId",userData.getUserId());
                        List<Map> paramsList = new ArrayList<>();
                        paramsList.add(sourceMap);
                    String paramsT = "data="+ JSON.toJSONString(paramsList);
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8"));
                    String response=null;
                    try{
                        response = (String) restTemplateUtil.execute(callUrlConfig.getGateWayHost(), callUrlConfig.getVolidateUseNumUrl(), HttpMethod.POST, new GatewayRequestCallback(paramsT,headers), new GatewayResponseExecutor());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    System.out.print(response);
                        JSONObject parse = (JSONObject) JSONObject.parse(response);
                        if(parse.getString("code").equals(ChangeConstants.ERROR_CODE)) throw new PlaceOrderException(ChangeConstants.ERROR_CODE,parse.getString("message"));
                        JSONArray array = parse.getJSONArray("result");
                        if(array==null||array.size()==0){
                            iFreePayService.doDetailOrder(osdto,orderNum,sdto,pdto,wdto,userData,id);
                        }else{
                            JSONObject temp = array.getJSONObject(0);
                            int useTime = temp.getIntValue("unused");
                            if(useTime<=0)iFreePayService.doDetailOrder(osdto,orderNum,sdto,pdto,wdto,userData,id);
                            else throw new PlaceOrderException("该资源已经订购");
                        }
                }

            }
//            return  new RtnData("000000","下单成功","",null);
//        }catch (Exception e){
//            e.printStackTrace();
//            return new RtnData("999999","下单失败","error",null);
//
//        }
//                if(osdto.getSourceType().equals(ChangeConstants.DATA_TYPE)){
//                    //免费数据资源按时间
//                    Map<String,Object> confMap = new HashMap<>();
//                    confMap.put("userId",userId);
//                    confMap.put("sourceId",sdto.getSourceId());
//                    confMap.put("nowDate",new Date());
//                    confMap.put("sourceType",ChangeConstants.DATA_TYPE);
//                    int count =  routerRuleMapper.getByAllId(confMap);
//                    if(count >0) {
//                        return new RtnData("999999","该资源已经下单","error",null);
//                    }
//                    else{
//                        iFreePayService.doDetailOrder(osdto,orderNum,sdto,pdto,userData,id);
//                    }
//                }else{
//
//
//                    // 套餐按次数
//                   // if(pdto.getChargeType().equals(ChangeConstants.CHARGE_TYPE_BY_FREQUENCY)){
//                    if(pdto.getDefaultUseTime()!=0){
//
//                    }else if(pdto.getDealyTime()!=0){
//                    //}else if(pdto.getChargeType().equals(ChangeConstants.CHARGE_TYPE_BY_TIME)){
//                        //按时间
//                        Map<String,Object> confMap = new HashMap<>();
//                        confMap.put("userId",userId);
//                        confMap.put("sourceId",sdto.getSourceId());
//                        confMap.put("nowDate",new Date());
//                        confMap.put("sourceType",ChangeConstants.API_TYPE);
//                        int count =  routerRuleMapper.getByAllId(confMap);
//                        if(count >0){
//                            return new RtnData("999999","该资源已经下单","error",null);
//                        }
//                        else{
//                            iFreePayService.doDetailOrder(osdto,orderNum,sdto,pdto,userData,id);}
//                    }
//                }

    }
}
