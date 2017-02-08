package com.digitalchina.common.service;
import com.digitalchina.dao.ApiOfflineMeetDao;
import com.digitalchina.domain.ApiOfflineMeet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * API服务信息服务类
 */
@Service
public class ApiOfflineMeetService {

    @Autowired
    private ApiOfflineMeetDao apiOfflineMeetDao;

    public int insert(ApiOfflineMeet apiOfflineMeet){
        return apiOfflineMeetDao.insert(apiOfflineMeet);
    }

}
