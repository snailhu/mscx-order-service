package com.digitalchina.common.service.Impl;

import com.digitalchina.common.service.IPayLogService;
import com.digitalchina.dao.PayLogMapper;
import com.digitalchina.domain.OrderInfo;
import com.digitalchina.domain.PayLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Snail on 2016/12/5.
 */
@Service
public class IPayLogServiceImpl implements IPayLogService {

    @Autowired
    PayLogMapper payLogMapper;

    @Override
    public void addPayLog(OrderInfo orderInfo) {
        PayLog payLog =  new PayLog();
        payLog.setPayCount(orderInfo.getOrderCashTotal());
        payLog.setPayer(orderInfo.getOrderUserName());
        payLog.setPayType(orderInfo.getPayType());
        payLog.setPayChannel("2");
        payLog.setCreatedTime(new Date());
        payLog.setArea(orderInfo.getArea());
        payLog.setPayStatus(orderInfo.getOrderStatus());
        //payLog.setCreatedBy();
        //payLog.setPayDate(new Date());
        payLog.setOrderNum(orderInfo.getOrderNum());
        payLogMapper.insert(payLog);
    }

    @Override
    public List<PayLog> getPayLogByTime(Date startTime, Date endTime) {
        Map<String,Object> conf = new HashMap<>();
        conf.put("startTime",startTime);
        conf.put("endTime",endTime);
        return payLogMapper.getPayLogByTime(conf);
    }
}
