package com.digitalchina.dto;

/**
 * Created by Snail on 2016/12/7.
 */
public class PaySendDto {
    //应用唯一标识
    String appId;
    //支付类型
    String channel;
    //商户订单号（商户系统唯一）StringNum
    String bino;
    //支付金额（单位：分）
    Integer totalFee;
    //支付标题
    String title;
    //支付宝回掉接口
    String returnUrl;
    //商品展示地址
    String showUrl;
    //支付宝二维码支付类型
    String qrPayMode;
    //订单失效日期
    Integer billTimeout;
    //	是否尝试掉起支付宝APP原生支付
     boolean useApp;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getBino() {
        return bino;
    }

    public void setBino(String bino) {
        this.bino = bino;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    public String getQrPayMode() {
        return qrPayMode;
    }

    public void setQrPayMode(String qrPayMode) {
        this.qrPayMode = qrPayMode;
    }

    public Integer getBillTimeout() {
        return billTimeout;
    }

    public void setBillTimeout(Integer billTimeout) {
        this.billTimeout = billTimeout;
    }

    public boolean isUseApp() {
        return useApp;
    }

    public void setUseApp(boolean useApp) {
        this.useApp = useApp;
    }
}
