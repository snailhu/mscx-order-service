package com.digitalchina.common.service;

import com.digitalchina.domain.OrderInfo;
import com.digitalchina.domain.PayLog;

import java.util.Date;
import java.util.List;

/**
 * Created by Snail on 2016/12/5.
 */
public interface IPayLogService {

    void addPayLog(OrderInfo orderInfo);

    public List<PayLog> getPayLogByTime(Date startTime, Date endTime);
}
