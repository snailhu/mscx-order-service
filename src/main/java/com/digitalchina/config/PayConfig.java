package com.digitalchina.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Snail on 2016/12/8.
 */
@Component
@ConfigurationProperties(prefix = "pay", locations = "classpath:payconfig.properties")
public class PayConfig {

    String payUrl;

    String payAppkey;

    String paySecretkey;

    String payAppid;

    String callBackUrl;

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getPayAppid() {
        return payAppid;
    }

    public void setPayAppid(String payAppid) {
        this.payAppid = payAppid;
    }

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    public String getPayAppkey() {
        return payAppkey;
    }

    public void setPayAppkey(String payAppkey) {
        this.payAppkey = payAppkey;
    }

    public String getPaySecretkey() {
        return paySecretkey;
    }

    public void setPaySecretkey(String paySecretkey) {
        this.paySecretkey = paySecretkey;
    }
}
