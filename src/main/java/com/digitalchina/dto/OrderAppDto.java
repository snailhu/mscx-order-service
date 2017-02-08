package com.digitalchina.dto;

/**
 * Created by Snail on 2017/1/12.
 */
public class OrderAppDto {
    //app名称
    String  appName;

    //app类型
    String  appType;

    //费用
    String price;

    //服务商
    String provideName;

    //浏览次数
    String BrowNum;

    //申请时间
    String  applyTime;

    //资源的id
    int sourceId;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProvideName() {
        return provideName;
    }

    public void setProvideName(String provideName) {
        this.provideName = provideName;
    }

    public String getBrowNum() {
        return BrowNum;
    }

    public void setBrowNum(String browNum) {
        BrowNum = browNum;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
