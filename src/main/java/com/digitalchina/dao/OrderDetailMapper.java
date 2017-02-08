package com.digitalchina.dao;

import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.OrderDetailExample;
import java.util.List;
import java.util.Map;

import com.digitalchina.dto.SourceIdCountDto;
import com.digitalchina.dto.StatisticsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDetailMapper {


    int insert(OrderDetail record);

    OrderDetail findOrderDetailBySourceId (Integer id);

    OrderDetail findOrderDetailByDetailId (Integer orderDetailId);

    int countBySourcdIdOrPackId(Map<String,Object> sourcdIdMap);

    int countByTime(Map<String,Object> sourcdIdMap);

    List<OrderDetail> getOrderDetailByOrderNum(String orderNum);

    List<OrderDetail> getOrderDetailByConf(Map<String,Object> confMap);

    OrderDetail getOrderDetailBySourceId(Integer resourceId );

    int getOrderListApiCount(Map<String,Object> confMap);

    //获取我的数据
    List<OrderDetail> getOrderDetailData(Map<String,Object> confMap);

    //获取我的数据总数
    int getOrderDetailDataCount(Map<String,Object> confMap);

    //获取本次订单支付成功后数据资源的统计
    List<SourceIdCountDto> getCountSource();

    //获取我的api总数
    int getSelfApiCount(Map<String,Object> conf);

    //根据用户的id 以及订单号获取订单详情
    List<OrderDetail> getByUserIdAndOrderNum(Map<String ,Object> orderMap);

    //根据资源类型id统计申请次数 用户数量
    List<Map> getSourceStatistics(Map<String ,Object> conf);

    //根据时间获取订单的详情
    List<OrderDetail> getOrderDetailByTime(Map<String ,Object> conf);

    //获取某个用户购买的某个api套餐的总数
    int getSelfApiChargeCount(Map<String ,Object> conf);

    //获取资源统计
    List<StatisticsDto> getCountStatisticsSource();

    //获取用户的订单信息
    List<Map> getOrderDetailByUserIds(Map<String,Object> conf);
//    List<Map> getOrderDetailByUserIds(String userId);
}