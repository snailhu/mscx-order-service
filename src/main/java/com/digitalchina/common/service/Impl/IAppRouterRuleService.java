package com.digitalchina.common.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.service.IRouterRuleSeivice;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.dao.RouterRuleMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.RouterRule;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.PackageDto;
import com.digitalchina.dto.SourceDto;
import com.digitalchina.dto.WeiAppDto;
import com.digitalchina.resttemplate.httpclient.RestTemplateWithHttpClientUtil;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Snail on 2017/1/11.
 */
@Service
public class IAppRouterRuleService {

    @Autowired
    RouterRuleMapper routerRuleMapper;

    @Autowired
    ISourceServieImpl iSourceServie;
    @Autowired
    IRouterRuleSeivice iRouterRuleSeivice;

    @Autowired
    ApiServiceImpl apiService;
    @Autowired
    CallUrlConfig callUrlConfig;
    @Autowired
    RestTemplateWithHttpClientUtil httpClientUtil;
    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;


    public void doRouterRule(OrderDetail orderDetail,WeiAppDto wdto, UserData userData) {

        JSONObject parse = (JSONObject) JSONObject.parse( orderDetail.getSourceJson());
        //按次
        if(wdto.getChargeMethod().equals(ChangeConstants.CHARGE_TYPE_BY_FREQUENCY)){

            int effective_number=0;
//            for(int i=0;i<orderDetail.getItemNumber();i++){
//                if(orderDetail.getResourceDelayTime()!=null){
//                    effective_number+=orderDetail.getItemNumber()*(parse.getIntValue("chargeCount"));
//                }
//            }
            if(parse.getInteger("chargeCount")!=null){
                effective_number=orderDetail.getItemNumber()*(parse.getIntValue("chargeCount"));
            }
            RouterRule rr = new RouterRule();
            Date startDate =new Date();
            Date endDate =null;
            if(parse.getString("invokeLimit")!=null){
                endDate = DateUtil.getAddCountMonth(startDate,parse.getIntValue("invokeLimit"));
            }else{
                endDate = DateUtil.getAddCountMonth(startDate,ChangeConstants.DEFAULT_USR_TIME);
            }
            rr.setSourceType(ChangeConstants.WAI_APP_TYPE);
            rr.setEffectiveNumber(effective_number);
            rr.setEffectiveTime(startDate);
            rr.setIneffectiveTime(endDate);
            rr.setCreatedTime(new Date());
            rr.setOrderDetailId(orderDetail.getId());
            rr.setArea(orderDetail.getArea());
            rr.setSourceId(wdto.getSourceId());
            rr.setOrderUserId(userData.getUserId());
            rr.setOrderNum(userData.getTemNum());
            rr.setSourceStatus(ChangeConstants.SOURCE_ROUTER_CREATE);
            routerRuleMapper.insert(rr);
            if(wdto.getSourcePrice()==0){
                iRouterRuleSeivice.pushRule(rr,orderDetail);
                apiService.doPushStatic();
            }

        }//按时间
        else if(wdto.getChargeMethod().equals(ChangeConstants.CHARGE_TYPE_BY_TIME)){
            RouterRule rrs =routerRuleMapper.getLastRuleByDetailId(orderDetail.getId());
            Date startDate=null;
            Date endDate=null;
            for(int i=0;i<orderDetail.getItemNumber();i++){
                if(i==0){
                    if(rrs!=null){
                        startDate=rrs.getIneffectiveTime();
                    }else{
                        startDate = new Date();
                    }
                    if(parse.getInteger("chargeCount")!=null){
                        endDate = DateUtil.getAddCountMonth( startDate,parse.getIntValue("chargeCount"));
                    }else{
                        endDate = DateUtil.getAddCountMonth( startDate,ChangeConstants.DEFAULT_USR_TIME);
                    }

                }else{
                    startDate=endDate;
                    if(parse.getInteger("chargeCount")!=null){
                        endDate = DateUtil.getAddCountMonth( startDate,parse.getIntValue("chargeCount"));
                    }else{
                        endDate = DateUtil.getAddCountMonth( startDate,ChangeConstants.DEFAULT_USR_TIME);
                    }
                }
                int effective_number = ChangeConstants.NO_FREQUENCY_LIMIT;
                if(parse.getString("invokeLimit")!=null){
                    effective_number=parse.getIntValue("invokeLimit");
                }
                RouterRule rr = new RouterRule();
                rr.setSourceType(ChangeConstants.WAI_APP_TYPE);
                rr.setEffectiveNumber(effective_number);
                rr.setEffectiveTime(startDate);
                rr.setIneffectiveTime(endDate);
                rr.setCreatedTime(new Date());
                rr.setOrderDetailId(orderDetail.getId());
                rr.setSourceId(wdto.getSourceId());
                rr.setArea(orderDetail.getArea());
                rr.setOrderUserId(userData.getUserId());
                rr.setOrderNum(userData.getTemNum());
                rr.setSourceStatus(ChangeConstants.SOURCE_ROUTER_CREATE);
                routerRuleMapper.insert(rr);
                if(wdto.getSourcePrice()==0){
                    iRouterRuleSeivice.pushRule(rr,orderDetail);
                    apiService.doPushStatic();
                }
            }
        }
    }
}
