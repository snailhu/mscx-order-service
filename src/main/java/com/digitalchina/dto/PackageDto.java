package com.digitalchina.dto;

/**
 * Created by Snail on 2016/12/1.
 */
public class PackageDto {

    String area;

    Integer  packageId;

    String  packageName;

    String  packageType;

    double  packagePrice;

    int defaultUseTime;

    Integer sourceId;

    Integer  dealyTime;

    String chargeType;

    boolean isFree;

    String apiServiceDesc;//api资源描述

    String  logoUrl;//logl 路径

    String accessCnt;//服务接入量

    String applyCnt;//服务申请量

    String status;

    String chargeMethod;

    String unitPrice;

    String apiJson;

    String charRuleName;

    Integer countLimit;

    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCountLimit() {
        return countLimit;
    }

    public void setCountLimit(Integer countLimit) {
        this.countLimit = countLimit;
    }

    public String getCharRuleName() {
        return charRuleName;
    }

    public void setCharRuleName(String charRuleName) {
        this.charRuleName = charRuleName;
    }

    public String getApiJson() {
        return apiJson;
    }

    public void setApiJson(String apiJson) {
        this.apiJson = apiJson;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getChargeMethod() {
        return chargeMethod;
    }

    public void setChargeMethod(String chargeMethod) {
        this.chargeMethod = chargeMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApiServiceDesc() {
        return apiServiceDesc;
    }

    public void setApiServiceDesc(String apiServiceDesc) {
        this.apiServiceDesc = apiServiceDesc;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getAccessCnt() {
        return accessCnt;
    }

    public void setAccessCnt(String accessCnt) {
        this.accessCnt = accessCnt;
    }

    public String getApplyCnt() {
        return applyCnt;
    }

    public void setApplyCnt(String applyCnt) {
        this.applyCnt = applyCnt;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getDealyTime() {
        return dealyTime;
    }

    public void setDealyTime(Integer dealyTime) {
        this.dealyTime = dealyTime;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public double getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(double packagePrice) {
        this.packagePrice = packagePrice;
    }

    public int getDefaultUseTime() {
        return defaultUseTime;
    }

    public void setDefaultUseTime(int defaultUseTime) {
        this.defaultUseTime = defaultUseTime;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }
}
