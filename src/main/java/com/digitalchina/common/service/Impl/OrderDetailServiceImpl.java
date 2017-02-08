package com.digitalchina.common.service.Impl;

import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.dao.IdGeneratorDao;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.PackageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snail on 2016/12/30.
 */
@Service
public class OrderDetailServiceImpl {

    @Autowired
    OrderDetailMapper orderDetailMapper;

    //获取用户购买的api套餐的总数
    int getSelfApiChargeCount(Map<String,Object> conf){

        return orderDetailMapper.getSelfApiChargeCount(conf);
    }

    //根据订单详情id获取丁订单详情
    public OrderDetail findOrderDetailByDetailId( int id){
        return  orderDetailMapper.findOrderDetailByDetailId(id);
    }
}
