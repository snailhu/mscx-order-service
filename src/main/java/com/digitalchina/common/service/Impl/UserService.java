package com.digitalchina.common.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.common.util.HttpClientUtil;
import com.digitalchina.config.UserServiceConfig;
import com.digitalchina.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Snail on 2016/12/9.
 */
@Service
public class UserService {
    @Autowired
    UserServiceConfig userServiceConfig;

    /**
     * 根据id获取用户信息
     */
    public UserInfo getUserInfoById(String id){
        String url = String.format("%s?userId=%s", userServiceConfig.getCheckUrl(), id);
        String response = HttpClientUtil.doGet(url, userServiceConfig.getTimeout()*1000,
                null, userServiceConfig.getEncoding());
        JSONObject parse = (JSONObject) JSONObject.parse(response);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(parse.getJSONObject("result").getJSONObject("userInfo").getJSONObject("userCertificationInfo").getString("name"));
        userInfo.setArea(parse.getJSONObject("result").getString("area"));
        userInfo.setId(id);
        return userInfo;
    }
}
