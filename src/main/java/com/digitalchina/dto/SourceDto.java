package com.digitalchina.dto;

/**
 * 数据资源dto
 * Created by Snail on 2016/12/1.
 */
public class SourceDto {

    String area;

    Integer  sourceId;

    String  sourceName;

    double  sourcePrice;

    String  sourceType;

    Integer defaultDelayDate;

    Integer defaultUseTime;

    boolean isFree;

    String updateTime;

    String chargeType;

    String status;

    String desc;

    String createdBy;
    String orgName;

    String sourceJson;

    public String getSourceJson() {
        return sourceJson;
    }

    public void setSourceJson(String sourceJson) {
        this.sourceJson = sourceJson;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
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

    public Integer getDefaultDelayDate() {
        return defaultDelayDate;
    }

    public void setDefaultDelayDate(Integer defaultDelayDate) {
        this.defaultDelayDate = defaultDelayDate;
    }

    public Integer getDefaultUseTime() {
        return defaultUseTime;
    }

    public void setDefaultUseTime(Integer defaultUseTime) {
        this.defaultUseTime = defaultUseTime;
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

    public Integer getSourceId() {
        return sourceId;
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
}
