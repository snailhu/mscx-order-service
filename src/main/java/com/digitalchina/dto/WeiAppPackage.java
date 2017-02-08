package com.digitalchina.dto;

/**
 * Created by Snail on 2017/1/11.
 */
public class WeiAppPackage {
    Integer appId;//资源
    Integer chargeRuleId;// 套餐

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getChargeRuleId() {
        return chargeRuleId;
    }

    public void setChargeRuleId(Integer chargeRuleId) {
        this.chargeRuleId = chargeRuleId;
    }
}
