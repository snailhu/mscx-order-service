package com.digitalchina.dto;

/**
 * Created by Snail on 2016/12/6.
 */
public class OrderApiDto {

    //api名称
    String  apiName;

    //api描述
    String  apiDesc;

    //申请时间
    String  applyTime;

    //收费类型
    String  chargeType;

    //使用次数
    int  totalTime;

    //剩余次数
    int  remainTime;

    //资源的id
    int sourceId;

    //套餐的id
    int sourcePakcageId;

    String  logoUrl;//logl 路径

    String viewCnt;//服务访问量

    String applyCnt;//服务接入量

    String typeStatus;

    String price;

    String chargeCount;

    String status;//资源的状态

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTypeStatus() {
        return typeStatus;
    }

    public void setTypeStatus(String typeStatus) {
        this.typeStatus = typeStatus;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getChargeCount() {
        return chargeCount;
    }

    public void setChargeCount(String chargeCount) {
        this.chargeCount = chargeCount;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getViewCnt() {
        return viewCnt;
    }

    public void setViewCnt(String viewCnt) {
        this.viewCnt = viewCnt;
    }

    public String getApplyCnt() {
        return applyCnt;
    }

    public void setApplyCnt(String applyCnt) {
        this.applyCnt = applyCnt;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }

    public int getSourcePakcageId() {
        return sourcePakcageId;
    }

    public void setSourcePakcageId(int sourcePakcageId) {
        this.sourcePakcageId = sourcePakcageId;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiDesc() {
        return apiDesc;
    }

    public void setApiDesc(String apiDesc) {
        this.apiDesc = apiDesc;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }
}
