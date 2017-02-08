package com.digitalchina.dto;

/**
 * Created by Snail on 2016/12/17.
 */
public class IsFreePurchaseDto {
    String userId;
    int effectiveNum;
    int countNum;
    String sourceStatus;

    public String getSourceStatus() {
        return sourceStatus;
    }

    public void setSourceStatus(String sourceStatus) {
        this.sourceStatus = sourceStatus;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getEffectiveNum() {
        return effectiveNum;
    }

    public void setEffectiveNum(int effectiveNum) {
        this.effectiveNum = effectiveNum;
    }

    public int getCountNum() {
        return countNum;
    }

    public void setCountNum(int countNum) {
        this.countNum = countNum;
    }
}
