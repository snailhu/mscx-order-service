package com.digitalchina.dto;

/**
 * Created by Snail on 2016/12/13.
 */
public class SourceIdCountDto {
    Integer resourceId;
    //用户访问两
    Integer userNum;
    //被申请次数
    Integer applyNum;
    //资源类型
    String  resourceType;
    //api类型
    String chargeRuleType;

    public String getChargeRuleType() {
        return chargeRuleType;
    }

    public void setChargeRuleType(String chargeRuleType) {
        this.chargeRuleType = chargeRuleType;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getUserNum() {
        return userNum;
    }

    public void setUserNum(Integer userNum) {
        this.userNum = userNum;
    }

    public Integer getApplyNum() {
        return applyNum;
    }

    public void setApplyNum(Integer applyNum) {
        this.applyNum = applyNum;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
