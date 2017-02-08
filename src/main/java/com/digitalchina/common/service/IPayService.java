package com.digitalchina.common.service;

import com.digitalchina.domain.PayLog;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.*;

import java.util.List;

/**
 * Created by Snail on 2016/12/1.
 */
public interface IPayService {

    public double doDetailOrder(OmitSourceDto osdto, String orderNum, SourceDto sourceDto, PackageDto packageDto, WeiAppDto wdto,UserData userData, int order_id);

    //根据成功的订单号创建路由规则
    public void doRouterRule(String orderNum,UserData userData);


}
