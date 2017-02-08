package com.digitalchina.common.service.Impl;

import com.digitalchina.common.service.IRouterRuleSeivice;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.config.CallUrlConfig;
import com.digitalchina.dao.RouterRuleMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.RouterRule;
import com.digitalchina.domain.UserData;
import com.digitalchina.dto.PackageDto;
import com.digitalchina.dto.SourceDto;
import com.digitalchina.dto.WeiAppDto;
import com.digitalchina.resttemplate.httpclient.RestTemplateWithHttpClientUtil;
import com.digitalchina.resttemplate.ribbon.retryable.RetryableLoadbalancedRestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Snail on 2017/1/11.
 */
@Service
public class IDataRouterRuleService {

    @Autowired
    RouterRuleMapper routerRuleMapper;

    @Autowired
    ISourceServieImpl iSourceServie;
    @Autowired
    IRouterRuleSeivice iRouterRuleSeivice;

    @Autowired
    ApiServiceImpl apiService;
    @Autowired
    CallUrlConfig callUrlConfig;
    @Autowired
    RestTemplateWithHttpClientUtil httpClientUtil;
    @Autowired
    RetryableLoadbalancedRestTemplateUtil restTemplateUtil;

    public void doRouterRule(OrderDetail orderDetail, SourceDto sdto,UserData userData) {

        Calendar rightNow = Calendar.getInstance();
        Date startDate = rightNow.getTime();
        rightNow.add(Calendar.YEAR, ChangeConstants.DEFAULT_USR_TIME);
        Date endDate =rightNow.getTime();
        int effective_number= ChangeConstants.NO_FREQUENCY_LIMIT;;
        RouterRule rr = new RouterRule();
        rr.setSourceType(ChangeConstants.DATA_TYPE);
        rr.setEffectiveNumber(effective_number);
        rr.setEffectiveTime(startDate);
        rr.setIneffectiveTime(endDate);
        rr.setCreatedTime(new Date());
        rr.setOrderDetailId(orderDetail.getId());
        rr.setSourceId(sdto.getSourceId());
        rr.setArea(orderDetail.getArea());
        rr.setOrderUserId(userData.getUserId());
        rr.setOrderNum(userData.getTemNum());
        rr.setSourceStatus(ChangeConstants.SOURCE_ROUTER_CREATE);
        routerRuleMapper.insert(rr);
    }
}
