package com.digitalchina.common.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.GatewayRequestCallback;
import com.digitalchina.common.GatewayResponseExecutor;
import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.RtnData;
import com.digitalchina.common.service.IOrderService;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;
import com.digitalchina.exception.PlaceOrderException;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Snail on 2017/1/12.
 */
@Service
public class IAppPurchaseOrNotService {

    @Autowired
    IOrderService iOrderService;

    @Autowired
    ISourceServieImpl iSourceServie;

    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;

    @Autowired
    CallUrlConfig callUrlConfig;

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    OrderDetailServiceImpl orderDetailService;

    public RtnData havePurchase(OmitSourceDto osdto, UserData userData) {

        IsFreePurchaseDto isFreePurchaseDto = iOrderService.isFreePurchase(osdto,userData);
        WeiAppPackage weiAppPackage = new WeiAppPackage();
        weiAppPackage.setAppId(osdto.getSourceId());
        weiAppPackage.setChargeRuleId(osdto.getChar_rule_id());
        List<WeiAppPackage> weiAppPackages = new ArrayList<>();
        weiAppPackages.add(weiAppPackage);
        Map<String, WeiAppDto> weiAppDtoMap = iSourceServie.getWeiAppPackageMap(weiAppPackages, userData);
        WeiAppDto wdto = weiAppDtoMap.get(osdto.getSourceId() + "" + osdto.getChar_rule_id());
        if(wdto.isFree()){
            if(isFreePurchaseDto==null)return new RtnData(ChangeConstants.SUCCESS_CODE, "可以订购", "OK", ChangeConstants.PERMISSION_ORDER);
            if((isFreePurchaseDto.getCountNum()>0) && (isFreePurchaseDto.getEffectiveNum()<0)){
                return new RtnData(ChangeConstants.SUCCESS_CODE, "已经订购", "OK", ChangeConstants.PAY);
            }else if((isFreePurchaseDto.getCountNum()>0) && (isFreePurchaseDto.getEffectiveNum()>0)){

                //向网关请求当前资源的可以使用的剩余次数
                Map<String,Object> sourceMap = new HashMap<>();
                sourceMap.put("resourceId",osdto.getSourceId());
                sourceMap.put("resourceType",osdto.getSourceType());
                sourceMap.put("timePoint", DateUtil.format(new Date()));
                sourceMap.put("userId",userData.getUserId());
                List<Map> paramsList = new ArrayList<>();
                paramsList.add(sourceMap);
                String paramsT = "data="+ JSON.toJSONString(paramsList);
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.parseMediaType("application/x-www-form-urlencoded; charset=UTF-8"));
                String response = null;
                try{
                    response = (String) restTemplateUtil.execute(callUrlConfig.getGateWayHost(), callUrlConfig.getVolidateUseNumUrl(), HttpMethod.POST, new GatewayRequestCallback(paramsT,headers), new GatewayResponseExecutor());
                }catch (Exception e){
                    throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"请求网关错误");
                }if(response==null)throw new PlaceOrderException(ChangeConstants.ERROR_CODE,"请求网关返回错误信息");
                JSONObject parse = (JSONObject) JSONObject.parse(response);
                if(parse.getString("code").equals(ChangeConstants.ERROR_CODE)) throw new PlaceOrderException(ChangeConstants.ERROR_CODE,parse.getString("message"));
                JSONArray array = parse.getJSONArray("result");
                if(array==null||array.size()==0){
                    return new RtnData(ChangeConstants.SUCCESS_CODE, "可以订购", "OK", ChangeConstants.PERMISSION_ORDER);
                }else{
                    JSONObject temp = array.getJSONObject(0);
                    int useTime = temp.getIntValue("unused");
                    if(useTime<=0)return new RtnData(ChangeConstants.SUCCESS_CODE, "可以订购", "OK", ChangeConstants.PERMISSION_ORDER);
                    else{return new RtnData(ChangeConstants.SUCCESS_CODE, "已经订购", "OK", ChangeConstants.PAY); }
                }
            }else  return new RtnData(ChangeConstants.SUCCESS_CODE, "可以订购", "OK", ChangeConstants.PERMISSION_ORDER);
        }
        else{
            if (wdto.getSourcePrice() == 0) {
                Map<String, Object> sourceMp = new HashMap<String, Object>();
                sourceMp.put("sourceId", osdto.getSourceId());
                sourceMp.put("packageId", osdto.getChar_rule_id());
                sourceMp.put("userId", userData.getUserId());
                int count = orderDetailMapper.countBySourcdIdOrPackId(sourceMp);
                if (count > 0) {
                    return new RtnData(ChangeConstants.SUCCESS_CODE, "已经订购", "OK", ChangeConstants.PAY);
                } else {return new RtnData(ChangeConstants.SUCCESS_CODE, "可以订购", "OK", ChangeConstants.PERMISSION_ORDER);}
            }
            //套餐是 按次 按时来累加的 是可以过来创建订单的
            if(wdto.getCountLimit()<0 || wdto.getCountLimit()==null){
                return new RtnData(ChangeConstants.SUCCESS_CODE, "可以订购", "OK", ChangeConstants.PERMISSION_ORDER);
            }
            //判断是否超购限购次数
            Map<String,Object> conf = new HashMap<>();
            conf.put("userId",userData.getUserId());
            conf.put("sourceId",wdto.getSourceId());
            conf.put("packageId",wdto.getPackageId());
            conf.put("payStatus", OrderStatusEnum.PAY_SUCCESS.getIndex());
            conf.put("resourceType", ChangeConstants.WAI_APP_TYPE);

            int countPay =  orderDetailService.getSelfApiChargeCount(conf);
            if(countPay<=wdto.getCountLimit()){
                return new RtnData(ChangeConstants.SUCCESS_CODE, "可以订购", "OK", ChangeConstants.PERMISSION_ORDER);
            }
            return new RtnData(ChangeConstants.SUCCESS_CODE, "已经订购", "OK", ChangeConstants.PAY);
        }
    }
}
