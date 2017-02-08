package com.digitalchina.common.service;

import com.digitalchina.common.RtnData;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.OrderInfo;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Snail on 2016/12/1.
 */
public interface IOrderService {


    public List<OrderInfo> getOrderListByUserId( String userId);

    void placeOrder(OmitOrderDto odto, UserData userData);

    void updateOrderInfo(OrderInfo orderInfo);

    List<OrderDetail> getOrderListByOrderNum(String orderNum);

    OrderInfo getOrderInfoByOrderNum(String orderNum);

    List<OrderInfoDto> getOrderInfoListByUser(Map<String,Object> confMap);

    int getOrderListCount( Map<String,Object>usrInfoMap);

    RtnData havePurchase(OmitSourceDto osdto,UserData userData);

    String getMyConsumeCash(UserData userData);

    //根据订单号获取订单详情
    OrderInfoDto getOrderDetail(String userId, String orderNum);

    public List<SourceIdCountDto> getCountSource();

    public IsFreePurchaseDto isFreePurchase(OmitSourceDto osdto,UserData userData);
}
