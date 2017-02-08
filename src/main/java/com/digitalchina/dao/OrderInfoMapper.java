package com.digitalchina.dao;

import com.digitalchina.domain.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderInfoMapper {
    int insert(OrderInfo record);

    OrderInfo findByOrderNum(String orderNum);
    OrderInfo findByOrderId(Integer orderId);

    void updateOrderInfo(OrderInfo orderInfo);
    List<OrderInfo> getOrderInfoListByUser(Map<String,Object> confMap);

    int getSelfOrderListCount(Map<String,Object> confMap);
    List<OrderInfo> getSelfOrderList(Map<String,Object> confMap);

    //获取消费金额总数
    Object getUserConsumeCash(Map<String,Object> confMap);
}