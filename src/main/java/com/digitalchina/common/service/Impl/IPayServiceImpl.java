package com.digitalchina.common.service.Impl;

import com.digitalchina.common.service.IPayService;
import com.digitalchina.common.service.IRouterRuleSeivice;

import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.dao.PayLogMapper;
import com.digitalchina.dao.RouterRuleMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.PayLog;
import com.digitalchina.domain.RouterRule;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Snail on 2016/12/2.
 */
@Service
public class IPayServiceImpl implements IPayService {

    @Autowired
    ISourceServieImpl iSourceServie;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    IRouterRuleSeivice iRouterRuleSeivice;
    @Autowired
    RouterRuleMapper routerRuleMapper;
    @Autowired
    PayLogMapper payLogMapper;

    @Override
    @Transactional
    public double doDetailOrder(OmitSourceDto osdto, String orderNum, SourceDto sdto, PackageDto pdto,WeiAppDto wdto, UserData userData,int order_id) {
        //组建订单资源明细
        OrderDetail orderDetail= new OrderDetail();
        orderDetail.setItemNumber(osdto.getItem_num());
        orderDetail.setCreatedBy(userData.getAccount());
        orderDetail.setCreatedTime(new Date());
        orderDetail.setResourceType(osdto.getSourceType());//放入的是大类型 01 02 03
        orderDetail.setOrderUserId(userData.getUserId());
        orderDetail.setOrderUserName(userData.getAccount());
        orderDetail.setResourceId(osdto.getSourceId());
        orderDetail.setChargeRuleId(osdto.getChar_rule_id());
        orderDetail.setOrderId(order_id);
        orderDetail.setChargeRuleId(osdto.getChar_rule_id());
      //  orderDetail.getResourceDelayTime()
        Double total_cash =0.0;
        //数据资源
        if(osdto.getChar_rule_id()==ChangeConstants.CHAR_RULE_ID && sdto!=null){
            // orderDetail.setChargeRuleDefaultTime(dto.getDefaultUseTime());null
            //orderDetail.setChargeRuleDes(sdto.);
            orderDetail.setItemCash(sdto.getSourcePrice());
            orderDetail.setFree(sdto.isFree());
            orderDetail.setArea(sdto.getArea());
            orderDetail.setChargeRuleDes(sdto.getDesc());
            orderDetail.setResourceType(ChangeConstants.DATA_TYPE);
            orderDetail.setResourceName(sdto.getSourceName());
            orderDetail.setOrderNum(orderNum);
            orderDetail.setChargeRuleType(ChangeConstants.DATA_TYPE);
            orderDetail.setSourceJson(sdto.getSourceJson());
            total_cash = sdto.getSourcePrice()*osdto.getItem_num();
        }else if(pdto!=null){//套餐资源

            orderDetail.setResourceName(pdto.getPackageName());
            orderDetail.setFree(pdto.isFree());
            orderDetail.setUnitPrice(pdto.getUnitPrice());
            orderDetail.setChargeRuleDes(pdto.getApiServiceDesc());
            orderDetail.setArea(pdto.getArea());
            orderDetail.setItemCash(pdto.getPackagePrice());
            orderDetail.setOrderNum(orderNum);
            orderDetail.setResourceType(ChangeConstants.API_TYPE);
            orderDetail.setChargeRuleType(pdto.getType());
            orderDetail.setSourceJson(pdto.getApiJson());
            orderDetail.setChargeRuleName(pdto.getCharRuleName());
            total_cash = pdto.getPackagePrice()*osdto.getItem_num();
        }else if(wdto!=null){
            orderDetail.setResourceName(wdto.getSourceName());
            orderDetail.setFree(wdto.isFree());
            orderDetail.setUnitPrice(wdto.getSourcePrice()+"");
            orderDetail.setChargeRuleDes(wdto.getChargeRuleDesc());
            orderDetail.setArea(wdto.getArea());
            orderDetail.setItemCash(wdto.getSourcePrice());
            orderDetail.setOrderNum(orderNum);
            orderDetail.setResourceType(ChangeConstants.WAI_APP_TYPE);
            orderDetail.setChargeRuleType(ChangeConstants.WAI_APP_TYPE);
            orderDetail.setSourceJson(wdto.getSourceJson());
            orderDetail.setChargeRuleName(wdto.getChargeRuleName());
            total_cash = wdto.getSourcePrice()*osdto.getItem_num();
        }
        orderDetail.setItemCashTotal(total_cash);
        orderDetailMapper.insert(orderDetail);
        int id = orderDetail.getId();
        iRouterRuleSeivice.doRouterRule(orderDetail,sdto,pdto,wdto,userData,id);
        return total_cash;
    }


    @Override
    public void doRouterRule(String orderNum,UserData userData) {
        //根据订单号去查询资
        List<RouterRule> routerRules = iRouterRuleSeivice.getRouterRuleByOrderNum(orderNum);
        for(RouterRule rr:routerRules){
            OrderDetail orderDetail = orderDetailMapper.findOrderDetailByDetailId(rr.getOrderDetailId());
            iRouterRuleSeivice.pushRule(rr,orderDetail);
        }
    }
}
