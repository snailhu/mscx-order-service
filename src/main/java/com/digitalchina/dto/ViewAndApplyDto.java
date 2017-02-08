package com.digitalchina.dto;

import io.swagger.models.auth.In;

/**
 * Created by Snail on 2017/1/16.
 */
public class ViewAndApplyDto {
    private Integer viewCnt;
    private Integer applyCnt;
    private Integer status;

    public Integer getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(Integer viewCnt) {
        this.viewCnt = viewCnt;
    }

    public Integer getApplyCnt() {
        return applyCnt;
    }

    public void setApplyCnt(Integer applyCnt) {
        this.applyCnt = applyCnt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
