package com.digitalchina.common.AgentController;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.RtnData;
import com.digitalchina.common.service.IOrderService;
import com.digitalchina.common.service.Impl.IPayLogServiceImpl;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.PayLog;
import com.digitalchina.dto.PayLogDto;
import com.digitalchina.dto.SourceIdCountDto;
import com.digitalchina.dto.StatisticsDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * Created by Snail on 2016/12/28.
 */
@Controller
public class PayLogController {
    @Autowired
    IPayLogServiceImpl iPayLogService;
    @Autowired
    IOrderService iOrderService;
    @Autowired
    OrderDetailMapper orderDetailMapper;

    @ApiOperation(value = "获取交易记录",
            notes = "header 中需要包含mscx-area-code, 用于指明获取哪个区域平台的数据 ",
            httpMethod = "GET",
            produces = "application/json")
    @ApiImplicitParams({
      @ApiImplicitParam(name = "resTime", dataType = "String", paramType = "query")
    })
    @RequestMapping(value = "/payLogList", method = RequestMethod.GET)
    @ResponseBody
   RtnData getPayLog(@RequestParam(required = false) String resTime){
        Date startDate=null;
        if(resTime!=null){
            startDate = DateUtil.format(resTime);
        }
        Date rTime =  new Date();
        List<PayLog> payLogs = iPayLogService.getPayLogByTime(startDate, rTime);
        List<PayLogDto> payLogDtos= new ArrayList<>();
        for(PayLog payLog:payLogs){
            List<OrderDetail> orderDetails = iOrderService.getOrderListByOrderNum(payLog.getOrderNum());
            for(OrderDetail orderDetail:orderDetails){
                PayLogDto payLogDto = new PayLogDto();
                payLogDto.setOrderNum(orderDetail.getOrderNum());
                payLogDto.setUserId(orderDetail.getOrderUserId());
                payLogDto.setUserName(orderDetail.getOrderUserName());
                payLogDto.setResourceId(orderDetail.getResourceId());
                payLogDto.setResourceName(orderDetail.getResourceName());
                payLogDto.setResourceType(orderDetail.getChargeRuleType());
                payLogDto.setPrice(orderDetail.getItemCash()+"");
                payLogDto.setAmount(orderDetail.getItemCashTotal()+"");
                payLogDto.setCount(orderDetail.getItemNumber());
                payLogDto.setArea(orderDetail.getArea());
                payLogDto.setPayDate(DateUtil.format(orderDetail.getCreatedTime()));
                if(orderDetail.getSourceJson()!=null){
                    JSONObject parse = (JSONObject) JSONObject.parse( orderDetail.getSourceJson());
                    if(parse.getInteger("earningCount")!=null){
                        payLogDto.setEarningCount((orderDetail.getItemNumber())*(parse.getDoubleValue("earningCount"))+"");
                    }if(parse.getInteger("feeCount")!=null){
                        payLogDto.setFeeCount((orderDetail.getItemNumber())*(parse.getDoubleValue("feeCount"))+"");
                    }
                    if(ChangeConstants.API_TYPE.equals(orderDetail.getResourceType())){
                        payLogDto.setCategoryName(parse.getString("categoryName"));
                        payLogDto.setCategoryId(parse.getString("categoryId"));
                        payLogDto.setProviderId(parse.getString("providerId"));
                        payLogDto.setProviderName(parse.getString("providerName"));
                    }else if(ChangeConstants.DATA_TYPE.equals(orderDetail.getResourceType())){
                        payLogDto.setCategoryName(parse.getString("categoryName"));
                        payLogDto.setCategoryId(parse.getString("categoryId"));
                        payLogDto.setProviderId(parse.getString("orgId"));
                        payLogDto.setProviderName(parse.getString("orgName"));
                    }else if(ChangeConstants.WAI_APP_TYPE.equals(orderDetail.getResourceType())){
                        payLogDto.setCategoryName(parse.getString("categoryName"));
                        payLogDto.setCategoryId(parse.getString("categoryId"));
                        payLogDto.setProviderId(parse.getString("providerId"));
                        payLogDto.setProviderName(parse.getString("providerName"));
                    }
                }
                payLogDtos.add(payLogDto);
            }
        }
        Map<String,Object> result = new HashMap<>(4,1.0f);
        result.put("resTime",DateUtil.format(rTime));
        result.put("list",payLogDtos);
        return new RtnData(result);
    }


    //获取统计
    @ApiOperation(value = "获取统计信息",
            notes = "header 中需要包含mscx-area-code, 用于指明获取哪个区域平台的数据 ",
            httpMethod = "GET",
            produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "resTime", required = false,dataType = "String", paramType = "query"
            )
    })
    @RequestMapping(value = "/getStatistics", method = RequestMethod.GET)
    @ResponseBody
    RtnData getStatistics(@RequestParam(required = false) String resTime){
        List<StatisticsDto> statisticsDtos = orderDetailMapper.getCountStatisticsSource();
        Map<String,Object> result = new HashMap<>();
        result.put("resTime",DateUtil.format(new Date()));
        result.put("list",statisticsDtos);
        return new RtnData(result);
    }



}
