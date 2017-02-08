package com.digitalchina.common.service;

import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.RouterRule;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;

import java.util.List;
import java.util.Map;

/**
 * Created by Snail on 2016/12/2.
 */
public interface IRouterRuleSeivice {

    void doRouterRule(OrderDetail orderDetail, SourceDto sdto, PackageDto packageDto, WeiAppDto wdto, UserData userData, int id);

    List<RouterRule> getRouterRuleByDetailId(Integer id);

    int getByAllId(Map<String,Object> confMap);
    //根据订单号后去路由规则
    List<RouterRule> getRouterRuleByOrderNum(String orderNum);

    //更新路由状态
    void updateStatus(int id,String status);

    public void pushRule(RouterRule rr,OrderDetail orderDetail);

    public  IsFreePurchaseDto getIfFreeDto(Map<String,Object> confMap);


}
