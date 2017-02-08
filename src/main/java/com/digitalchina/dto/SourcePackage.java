package com.digitalchina.dto;

/**
 * Created by Snail on 2016/12/9.
 */
public class SourcePackage {
    Integer apiServiceId;//资源
    Integer chargeRuleId;// 套餐

    public Integer getApiServiceId() {
        return apiServiceId;
    }

    public void setApiServiceId(Integer apiServiceId) {
        this.apiServiceId = apiServiceId;
    }

    public Integer getChargeRuleId() {
        return chargeRuleId;
    }

    public void setChargeRuleId(Integer chargeRuleId) {
        this.chargeRuleId = chargeRuleId;
    }
}
