package com.digitalchina.common.util;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * 发送http请求工具
 *
 * Created by hongkai on 2016/5/5.
 */
public class HttpSender {

    public static String post(String url, int httpConnectionTimeout, List<NameValuePair> params, Map<String,Object> header){
        CloseableHttpResponse response = null;
        HttpPost httpPost = null;
        //默认参数
        UrlEncodedFormEntity uefEntity;
        try {
            CloseableHttpClient httpClient = HttpClientUtil.getHttpClient();

            httpPost = new HttpPost(url);

            //设置参数
            if(params != null){
                uefEntity = new UrlEncodedFormEntity(params, "UTF-8");
                httpPost.setEntity(uefEntity);
            }

            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom()
                    .setSocketTimeout(httpConnectionTimeout)
                    .setConnectTimeout(httpConnectionTimeout)
                    .build();
            httpPost.setConfig(requestConfig);

            //设置http header
            if(header!=null){
                Iterator<String> iter=header.keySet().iterator();
                while(iter.hasNext()){
                    String key=iter.next();
                    httpPost.setHeader(key,header.get(key)==null?"":header.get(key).toString());
                }
            }

            response = httpClient.execute(httpPost);

            return EntityUtils.toString(response.getEntity());
        } catch (ConnectTimeoutException e) {
            throw new RuntimeException("连接超时", e);
        } catch (Exception e) {
            throw new RuntimeException
                    ("系统异常", e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
            }
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
            } catch (Exception e) {
            }
        }
    }
}
