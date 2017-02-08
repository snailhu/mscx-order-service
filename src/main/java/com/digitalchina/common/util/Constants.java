package com.digitalchina.common.util;

import java.util.HashMap;

/**
 * Created by xingding on 2016/10/29.
 */
public class Constants {
    /* 平台代码规范常量 */
    /**
     * 通用成功代码
     */
    public final static String RTN_CODE_SUCCESS = "000000";
    /**
     * 通用错误代码
     */
    public final static String RTN_CODE_FAIL = "999999";

    public final static String RTN_STATUS_SUCCESS = "OK";
    public final static String RTN_STATUS_ERROR = "ERROR";

    public final static String GATEWAY_USER_INFO_KEY = "mscx-user-data";
    public final static String GATEWAY_AREA_INFO_KEY = "mscx-area-code";


    public final static String OFFLINE_MEET_ACCEPT = "0";

    public final static String COMMA_SYMBOL = ",";

    /**
     * 通用错误信息
     */
    public final static String RTN_MESSAGE_ERROR = "请求发生异常";


    public interface API_TYPE{
        String DATA_API_TYPE="2";
        String TOOL_API_TYPE="3";
        String MODEL_API_TYPE="4";
    }
    public interface API_STATUS_CD{
        String API_VALID_CODE = "0";//已通过
        String API_OFFLINE_CODE = "1";//已下架
        String API_AUDITING_CODE = "2";//审核中
        String API_REJECTED_CODE = "3";//已拒绝
        String API_DELETED_CODE = "4";//已删除
        String API_INITAIL_CODE = "5";//待提交
    }
    public static final HashMap<String, String> API_STATUS_MAP = new HashMap<String, String>() {
        {
            put("0", "审核通过");
            put("1", "已下架");
            put("2", "审核中");
            put("3", "审核拒绝");
            put("4", "已删除");
            put("5", "待提交");
        }
    };

    public interface RECOMMEND_TYPE{
        String NAVIGATION_CODE="0";
        String RECOMMEND_CODE="1";
        String SELECTED_CODE="2";
    }

    public interface API_CHARGERULE_STATUS_CD{
        String NORMAL_CODE="01";
        String DELETED_CODE="02";
    }
    public interface API_CHARGERULE_TYPE_CD{
        String FREE_CODE="01";
        String CHARGE_CODE="02";
    }
    public interface CREATED_FROM_CD{
        String B_CLIENT="2";
        String C_CLIENT="1";
    }

    public interface RESOURCE_TYPE{
        String API="01";
    }

    public interface OPERATION_FLAG{
        String CREATE="C";
        String RETRIEVE="R";
        String UPDATE="U";
        String DELETE="D";
    }
    
}
