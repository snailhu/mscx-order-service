package com.digitalchina.dao;

import com.digitalchina.domain.PayLog;
import com.digitalchina.domain.PayLogExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PayLogMapper {

    int insert(PayLog record);

    List<PayLog> getPayLogByTime(Map<String,Object> conf);
}