package com.digitalchina.common.service.Impl;

import com.digitalchina.common.util.DateUtil;
import com.digitalchina.dao.IdGeneratorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Snail on 2016/12/18.
 */
@Service
public class GenerateService {
    @Autowired
    IdGeneratorDao idGeneratorDao;


    public String getOrderNum(String areaId){
        idGeneratorDao.increase();
        int serial = idGeneratorDao.current();
        String nineTime = DateUtil.getNineTime();
        String orderNum = areaId+nineTime+serial;
        return orderNum;
    }
}
