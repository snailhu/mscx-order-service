package com.digitalchina.dto;

/**
 * Created by Snail on 2016/12/6.
 */
public class WeiAppDto {
    String area;

    Integer  sourceId;

    String  sourceName;

    double  sourcePrice;

    String  sourceType;

    boolean isFree;

    String chargeType;

    String status;

    String desc;

    String sourceJson;

    String chargeStatus;

    String chargeRuleName;

    String chargeMethod;

    Integer countLimit;

    Integer packageId;

    String chargeRuleDesc;

    public String getChargeRuleDesc() {
        return chargeRuleDesc;
    }

    public void setChargeRuleDesc(String chargeRuleDesc) {
        this.chargeRuleDesc = chargeRuleDesc;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Integer getCountLimit() {
        return countLimit;
    }

    public void setCountLimit(Integer countLimit) {
        this.countLimit = countLimit;
    }

    public String getChargeMethod() {
        return chargeMethod;
    }

    public void setChargeMethod(String chargeMethod) {
        this.chargeMethod = chargeMethod;
    }

    public String getChargeRuleName() {
        return chargeRuleName;
    }

    public void setChargeRuleName(String chargeRuleName) {
        this.chargeRuleName = chargeRuleName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public double getSourcePrice() {
        return sourcePrice;
    }

    public void setSourcePrice(double sourcePrice) {
        this.sourcePrice = sourcePrice;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSourceJson() {
        return sourceJson;
    }

    public void setSourceJson(String sourceJson) {
        this.sourceJson = sourceJson;
    }

    public String getChargeStatus() {
        return chargeStatus;
    }

    public void setChargeStatus(String chargeStatus) {
        this.chargeStatus = chargeStatus;
    }
}
