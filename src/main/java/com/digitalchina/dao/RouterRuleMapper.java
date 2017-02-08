package com.digitalchina.dao;

import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.RouterRule;
import com.digitalchina.domain.RouterRuleExample;
import java.util.List;
import java.util.Map;

import com.digitalchina.dto.IsFreePurchaseDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RouterRuleMapper {


    int insert(RouterRule record);

    List<RouterRule> getRouterRule(Integer orderDetailId);

    RouterRule getLastRule();

    RouterRule getLastRuleByDetailId(Integer orderDetailId);

    int getByAllId(Map<String,Object> confMap);

    IsFreePurchaseDto getIfFreeDto(Map<String,Object> confMap);

    //根据订单号获取路由规则
    List<RouterRule> getRouterRuleByOrderNum(String orderNum);

    //更新路由接受状态
    void updateStatus(Map<String,Object> conf);

}