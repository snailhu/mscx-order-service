package com.digitalchina.common.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.OrderApiDto;
import com.digitalchina.dto.OrderAppDto;
import com.digitalchina.dto.SourcePackage;
import com.digitalchina.dto.WeiAppPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snail on 2017/1/12.
 */
@Service
public class WeiAppService {

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    ApiServiceImpl apiService;
    /**
     * 获取用户申请购买的app
     * @param orderDetailConfMap
     * @return
     */
    public List<OrderAppDto> getOrderDetailByUserIdAndType(Map<String,Object> orderDetailConfMap){
        List<OrderAppDto> orderAppDtos = new ArrayList<>();
        List<OrderDetail> orderDetails =  orderDetailMapper.getOrderDetailByConf(orderDetailConfMap);
        if((orderDetails==null) || (orderDetails.size()==0)) return orderAppDtos;
        Map<String,Object> sourceUse = new HashMap<>();
        List<Map> paramsList = new ArrayList<>();
        for(OrderDetail orderDetail:orderDetails){
            Map<String,Object> sourceMap = new HashMap<>();
            sourceMap.put("resourceId",orderDetail.getResourceId());
            sourceMap.put("resourceType",orderDetail.getResourceType());
            sourceMap.put("userId",orderDetail.getOrderUserId());
            paramsList.add(sourceMap);
        }
        Boolean isSuccess =apiService.doAllUseNumUrl(sourceUse,paramsList);
        for(OrderDetail orderDetail:orderDetails){
            OrderAppDto orderAppDto = new OrderAppDto();
            JSONObject parse = (JSONObject) JSONObject.parse( orderDetail.getSourceJson());
            orderAppDto.setApplyTime(orderDetail.getCreatedTime().getTime()+"");
            orderAppDto.setAppName(parse.getString("appName"));
            orderAppDto.setAppType(parse.getString("serviceChannel"));
            if(orderDetail.isFree()){
                orderAppDto.setPrice("免费");
            }else{
                orderAppDto.setPrice(orderDetail.getItemCashTotal()+"");
            }

            orderAppDto.setProvideName(parse.getString("providerName"));
            orderAppDto.setSourceId(orderDetail.getResourceId());

            if(isSuccess){
                Map<String,Object> access_apply = (Map<String, Object>) sourceUse.get(orderDetail.getResourceId());
                if(access_apply!=null){
                    if(access_apply.get("ApplyCnt")!=null && access_apply.get("AccessCnt")!=null ){
                        orderAppDto.setBrowNum(((int)access_apply.get("ApplyCnt")-(int)access_apply.get("AccessCnt"))+"");
                    }
                }
            }else{
                orderAppDto.setBrowNum("--");
            }

            orderAppDtos.add(orderAppDto);
        }
        return  orderAppDtos;
    }

}
