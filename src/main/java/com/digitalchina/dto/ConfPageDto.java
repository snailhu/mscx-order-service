package com.digitalchina.dto;

import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by Snail on 2016/12/6.
 */
public class ConfPageDto {

    Integer userId;

    List<Object> apiIdList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Object> getApiIdList() {
        return apiIdList;
    }

    public void setApiIdList(List<Object> apiIdList) {
        this.apiIdList = apiIdList;
    }
}
