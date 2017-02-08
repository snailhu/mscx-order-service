package com.digitalchina.common.util;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.domain.UserData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuyd on 2016/12/3.
 * 获取 门户router请求头中的信息
 */
public class HttpHeaderInfoUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpHeaderInfoUtil.class);

    /**
     * 获取请求头中的userId
     * @param request
     * @return
     */
//    static public String getCurrentUserId(HttpServletRequest request){
//        JSONObject data = getUserData(request);
//        String userId = data.getString("userId");
//        return userId;
//    }
//
//    /**
//     * 获取请求头中的account
//     * @param request
//     * @return
//     */
//    static public String getCurrentUserAccount(HttpServletRequest request){
//        JSONObject data = getUserData(request);
//        String account = data.getString("account");
//        return account;
//    }
//
//    static public String getCurrentUserName(HttpServletRequest request){
//        JSONObject data = getUserData(request);
//        String name = data.getString("name");
//        return name;
//    }

    static public UserData getUserData(HttpServletRequest request){
        String data = request.getHeader(ChangeConstants.HEADER_USERDATA);
        if(StringUtils.isEmpty(data)){
            logger.error("有一条缺少标准头信息的请求,RequestHeader有，" +
                            "Host:%s  Origin:%s  User-Agent:%s",
                    request.getHeader("Host"),
                    request.getHeader("Origin"),
                    request.getHeader("User-Agent"));
            throw new RuntimeException("非法请求");
        }
        return JSONObject.parseObject(data,UserData.class);
    }

    static public String getNameOrAccount(UserData userData){
        return StringUtils.isEmpty(userData.getName())?userData.getAccount():userData.getName();
    }

    static public String getAreaCode(HttpServletRequest request){
        String area = request.getHeader(ChangeConstants.HEADER_AREA);
        if(StringUtils.isEmpty(area)){
            logger.error("有一条缺少标准头信息的请求,RequestHeader有，" +
                            "Host:%s  Origin:%s  User-Agent:%s",
                    request.getHeader("Host"),
                    request.getHeader("Origin"),
                    request.getHeader("User-Agent"));
            throw new RuntimeException("非法请求");
        }
        return area;
    }
//    static private JSONObject getUserData(HttpServletRequest request){
//        String data = request.getHeader("user-digitalchina-data");
//        return JSONObject.parseObject(data);
//    }

}
