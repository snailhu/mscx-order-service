package com.digitalchina.common.service.Impl;

import com.digitalchina.common.OrderStatusEnum;
import com.digitalchina.common.util.ChangeConstants;
import com.digitalchina.common.util.DateUtil;
import com.digitalchina.dao.OrderDetailMapper;
import com.digitalchina.dao.OrderInfoMapper;
import com.digitalchina.domain.OrderDetail;
import com.digitalchina.domain.OrderInfo;
import com.digitalchina.dto.DownLoadDataDto;
import com.digitalchina.dto.OrderApiDto;
import com.digitalchina.dto.SourceDto;
import com.digitalchina.dto.WeiAppDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.WeakCacheKey;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Snail on 2016/12/6.
 */
@Service
public class DataServiceImpl {

    @Autowired
    OrderDetailMapper orderDetailMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    ISourceServieImpl iSourceServie;

    /**
     * 我的下载数据
     * @param orderDetailConfMap
     * @return
     */

    public List<DownLoadDataDto> getByUserIdAndSourceType(Map<String,Object> orderDetailConfMap){
        List<DownLoadDataDto> downLoadDataDtos = new ArrayList<>();
        List<Integer> sourceIdList =  new ArrayList<>();
        List<OrderDetail> orderDetails =  orderDetailMapper.getOrderDetailData(orderDetailConfMap);
        if(orderDetails!=null && orderDetails.size()>0){
            for(OrderDetail orderDetail:orderDetails){
//                OrderInfo orderInfo = orderInfoMapper.findByOrderId(orderDetail.getOrderId());
//                if(orderInfo.getOrderStatus()== OrderStatusEnum.PAY_SUCCESS.getIndex()){
                    DownLoadDataDto downLoadDataDto = new DownLoadDataDto();
                    downLoadDataDto.setId(orderDetail.getResourceId());
                    downLoadDataDto.setDataSourceName(orderDetail.getResourceName());
                    sourceIdList.add(orderDetail.getResourceId());
                    downLoadDataDtos.add(downLoadDataDto);
//                }
            }
            Map<Integer,SourceDto> sourceDtoMap = iSourceServie.getSourceMap(sourceIdList);
            for(DownLoadDataDto downLoadDataDto:downLoadDataDtos){
                if(sourceDtoMap!=null && sourceDtoMap.size()>0){
                    SourceDto sourceDto = sourceDtoMap.get(downLoadDataDto.getId());
                    if(sourceDto!=null){
                        downLoadDataDto.setUpdateTime(sourceDto.getUpdateTime());
                        downLoadDataDto.setDesc(sourceDto.getDesc());
                        downLoadDataDto.setDisplayer(sourceDto.getCreatedBy());
                        downLoadDataDto.setOrgName(sourceDto.getOrgName());
                    }
                }
            }
        }
        return downLoadDataDtos;
    }

    //获取我下载数据的总量
    public int getOrderDataListCount(String userId, String sourceType,int payStatus){
        Map<String,Object> dataConfMap = new HashMap<>();
        dataConfMap.put("userId",userId);
        dataConfMap.put("resourceType",sourceType);
        dataConfMap.put("payStatus",payStatus);
        return orderDetailMapper.getOrderDetailDataCount(dataConfMap);
    }

    public List<Map> getSourceStatistics(String dataIds,String resourceType) {
        Map<String,Object> conf = new HashMap<>();
        conf.put("dataIds",dataIds.split(","));
        conf.put("resourceType",resourceType);
        return orderDetailMapper.getSourceStatistics(conf);
    }

    //根据时间获取订单的详情
    public List<OrderDetail> getOrderDetailByTime(Date startTime, Date endTime){
        Map<String,Object> conf = new HashMap<>();
        conf.put("startTime",startTime);
        conf.put("endTime",endTime);
        conf.put("resourceType",ChangeConstants.DATA_TYPE);
        return orderDetailMapper.getOrderDetailByTime(conf);
    }
}
