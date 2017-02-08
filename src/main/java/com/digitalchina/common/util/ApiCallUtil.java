package com.digitalchina.common.util;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.misc.BASE64Encoder;

/**
 * Created by hongkai on 2016/1/21.
 */
public class ApiCallUtil {

    private static Logger logger = LoggerFactory.getLogger(ApiCallUtil.class);

    private static SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

    public static String call(String url, String appKey, String secretKey, Map<String,String> parameters) throws Exception {
        String params = "";
        params = new BASE64Encoder().encode(params.getBytes());

        final Map<String, Object> header = new HashMap<String, Object>();
        String method = "get";
        String contentType = "application/x-www-form-urlencoded";
        String date = sFormat.format(new Date());

        header.put("method", method);
        header.put("Content-Type", contentType);
        header.put("user-params", params);
        header.put("user-date", date);
        header.put("dceast-appkey", appKey);
        /**
         * 生成签名
         */
        String signData = SignatureUtil.buildSignData(params, date);
        String sign = SignatureUtil.buildSignature(appKey, secretKey, signData);
        header.put("authorization", sign);

        /**
         * 发送http请求
         */
        String response = HttpSender.post(url, 5000, getParams(parameters), header);
        return response;
    }

    private static List<NameValuePair> getParams(Map<String,String> parameters){
        List<NameValuePair> result = new ArrayList<>();
        for(final Map.Entry<String, String> entry : parameters.entrySet()){
            result.add(new NameValuePair() {
                @Override
                public String getName() {
                    return entry.getKey();
                }

                @Override
                public String getValue() {
                    return entry.getValue();
                }
            });
        }

        return result;
    }
}
