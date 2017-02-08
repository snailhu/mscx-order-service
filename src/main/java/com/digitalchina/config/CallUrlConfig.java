package com.digitalchina.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Snail on 2016/12/19.
 */
@Component
@ConfigurationProperties(locations = "classpath:callUrl.properties")
public class CallUrlConfig {

    //数据
    private String dataHost;
    private String dataListUrl;
    private String codeEncoding;
    private String timeout;
    //api
    private String apiHost;
    private String apiServiceDetailUrl;
    private String apiStatisticsUrl;
    private String apiBroAndApl;


    //    网关
    private String gateWayHost;
    private String VolidateUseNumUrl;
    private String pushRuleUrl;
    private String AllUseNumUrl;

    //    微服务
    private String appHost;
    private String appStatisticsUrl;
    private String appServiceDetailUrl;

    public String getApiBroAndApl() {
        return apiBroAndApl;
    }

    public void setApiBroAndApl(String apiBroAndApl) {
        this.apiBroAndApl = apiBroAndApl;
    }

    public String getAppHost() {
        return appHost;
    }

    public void setAppHost(String appHost) {
        this.appHost = appHost;
    }

    public String getAppStatisticsUrl() {
        return appStatisticsUrl;
    }

    public void setAppStatisticsUrl(String appStatisticsUrl) {
        this.appStatisticsUrl = appStatisticsUrl;
    }

    public String getAppServiceDetailUrl() {
        return appServiceDetailUrl;
    }

    public void setAppServiceDetailUrl(String appServiceDetailUrl) {
        this.appServiceDetailUrl = appServiceDetailUrl;
    }

    public String getGateWayHost() {
        return gateWayHost;
    }

    public void setGateWayHost(String gateWayHost) {
        this.gateWayHost = gateWayHost;
    }

    public String getVolidateUseNumUrl() {
        return VolidateUseNumUrl;
    }

    public void setVolidateUseNumUrl(String volidateUseNumUrl) {
        VolidateUseNumUrl = volidateUseNumUrl;
    }

    public String getPushRuleUrl() {
        return pushRuleUrl;
    }

    public void setPushRuleUrl(String pushRuleUrl) {
        this.pushRuleUrl = pushRuleUrl;
    }

    public String getAllUseNumUrl() {
        return AllUseNumUrl;
    }

    public void setAllUseNumUrl(String allUseNumUrl) {
        AllUseNumUrl = allUseNumUrl;
    }

    public String getApiStatisticsUrl() {
        return apiStatisticsUrl;
    }

    public void setApiStatisticsUrl(String apiStatisticsUrl) {
        this.apiStatisticsUrl = apiStatisticsUrl;
    }

    public String getDataHost() {
        return dataHost;
    }

    public void setDataHost(String dataHost) {
        this.dataHost = dataHost;
    }

    public String getDataListUrl() {
        return dataListUrl;
    }

    public void setDataListUrl(String dataListUrl) {
        this.dataListUrl = dataListUrl;
    }

    public String getCodeEncoding() {
        return codeEncoding;
    }

    public void setCodeEncoding(String codeEncoding) {
        this.codeEncoding = codeEncoding;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getApiHost() {
        return apiHost;
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public String getApiServiceDetailUrl() {
        return apiServiceDetailUrl;
    }

    public void setApiServiceDetailUrl(String apiServiceDetailUrl) {
        this.apiServiceDetailUrl = apiServiceDetailUrl;
    }
}
