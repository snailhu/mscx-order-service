package com.digitalchina.common.service.Impl;

import com.digitalchina.common.service.IFreePayService;
import com.digitalchina.common.service.IRouterRuleSeivice;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Snail on 2016/12/2.
 */
@Service

public class IFreePayServiceImpl implements IFreePayService{
    @Autowired
    ISourceServieImpl iSourceServie;
    @Autowired
    OrderDetailMapper orderDetailMapper;
    @Autowired
    IRouterRuleSeivice iRouterRuleSeivice;
    @Override
    @Transactional
    public double doDetailOrder(OmitSourceDto osdto, String orderNum, SourceDto sdto, PackageDto pdto, WeiAppDto wdto,UserData userData, int order_id) {
        //组建订单资源明细
        OrderDetail orderDetail= new OrderDetail();
        orderDetail.setItemNumber(osdto.getItem_num());
       // orderDetail.setCreatedBy("now_user");
        orderDetail.setCreatedTime(new Date());
        orderDetail.setItemCashTotal(0.0);
        orderDetail.setOrderNum(orderNum);
        orderDetail.setItemCash(0.0);
        orderDetail.setResourceType(osdto.getSourceType());//放入的是大类型 01 02 03
        orderDetail.setOrderUserId(userData.getUserId());
        orderDetail.setOrderUserName(userData.getAccount());
        orderDetail.setOrderId(order_id);
        orderDetail.setChargeRuleId(osdto.getChar_rule_id());
        orderDetail.setResourceId(osdto.getSourceId());
        if(osdto.getSourceType().equals(ChangeConstants.DATA_TYPE) && sdto!=null){//如果是数据资源
           // orderDetail.setChargeRuleDefaultTime(dto.getDefaultUseTime());null
            orderDetail.setFree(sdto.isFree());
            orderDetail.setArea(sdto.getArea());
            orderDetail.setDefaulTime(sdto.getDefaultUseTime());
            orderDetail.setItemCash(sdto.getSourcePrice());
            orderDetail.setResourceName(sdto.getSourceName());
            orderDetail.setSourceJson(sdto.getSourceJson());
            orderDetail.setChargeRuleType(ChangeConstants.DATA_TYPE);
        }else if(osdto.getSourceType().equals(ChangeConstants.API_TYPE) && pdto!=null){
            orderDetail.setFree(pdto.isFree());
            orderDetail.setArea(pdto.getArea());
            orderDetail.setUnitPrice(pdto.getUnitPrice());
            orderDetail.setDefaulTime(pdto.getDefaultUseTime());
            orderDetail.setItemCash(pdto.getPackagePrice());
            orderDetail.setResourceName(pdto.getPackageName());
            orderDetail.setChargeRuleType(pdto.getPackageType());
            orderDetail.setChargeRuleName(pdto.getCharRuleName());
            orderDetail.setChargeRuleDes(pdto.getApiServiceDesc());
            orderDetail.setSourceJson(pdto.getApiJson());
        }else if(osdto.getSourceType().equals(ChangeConstants.WAI_APP_TYPE) && wdto!=null){
            orderDetail.setFree(wdto.isFree());
            orderDetail.setArea(wdto.getArea());
            orderDetail.setItemCash(wdto.getSourcePrice());
            orderDetail.setResourceName(wdto.getSourceName());
            orderDetail.setChargeRuleType(wdto.getSourceType());
//            orderDetail.setChargeRuleType(osdto.getSourceType());
            orderDetail.setChargeRuleName(wdto.getChargeRuleName());
            orderDetail.setChargeRuleDes(wdto.getDesc());
            orderDetail.setSourceJson(wdto.getSourceJson());
        }
        orderDetailMapper.insert(orderDetail);
        int id = orderDetail.getId();
        //创建路由收费规则
       // OrderDetail oDetail =orderDetailMapper.findOrderDetailByDetailId(id);
        iRouterRuleSeivice.doRouterRule(orderDetail,sdto,pdto,wdto,userData,id);
        return 0;
    }
}
