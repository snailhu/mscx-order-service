package com.digitalchina.common.util;

/**
 * Created by Snail on 2016/12/6.
 */
public class ChangeConstants {

    public static final int CHAR_RULE_ID=-1;

    public static final int CHAR_BY_FREQUENCY=1;

    public static final int CHAR_BY_TIME=2;
    //  默认套餐 使用年限2年
    public static final int DEFAULT_USR_TIME = 24;

    //  默认套餐 使用年限2年
    public static final int DEFAULT_USR_TIME_YEAR = 2;

    //套餐不限次数时设置的值
    public static final int NO_FREQUENCY_LIMIT = -1;

    //数据模api类型
    public static final int  DATA_API_TYPE = 2;
    //工具模api类型
    public static final int  TOOL_API_TYPE = 3;
    //模型api类型
    public static final int  MODEL_API_TYPE = 4;
    //开放数据类型
    public static final int  OPEN_DATA_TYPE = 5;
    //微服务类型
    public static final String  WAI_APP_TYPE = "03";
    //api类型
    public static final String API_TYPE = "01";
    //数据类型
    public static final String  DATA_TYPE = "02";

    //套餐按时间
    public static final String  CHARGE_TYPE_BY_TIME = "04";
    //套餐按次数
    public static final String  CHARGE_TYPE_BY_FREQUENCY = "05";
    //套餐免费
    public static final String  CHARGE_TYPE_FREE = "01";

    /* 标准的请求头名称 */
    public final static String HEADER_USERDATA = "mscx-user-data";
    public final static String HEADER_AREA = "mscx-area-code";

    /*向路由推送状态*/

    //创建
    public final static String SOURCE_ROUTER_CREATE = "01";
    //支付验签
    public final static String SOURCE_ROUTER_PAY_VERIFY = "02";
    //是否推送
    public final static String SOURCE_ROUTER_PUSHORNOT = "03";
    //推送成功
    public final static String SOURCE_ROUTER_PUSH_OK = "04";
    //推送失败
    public final static String SOURCE_ROUTER_PUSH_ERROR = "05";

    //未付款
    public final static String  NOT_PAY = "01";
    //已付款
    public final static String  PAY = "02";
    //可以订购
    public final static String  PERMISSION_ORDER = "03";

    public final static String  ERROR_CODE = "999999";

    public final static String  SUCCESS_CODE = "000000";

    //chargeStatus 套餐被下架
    public final static String  CHARGE_STATUS_OUTLINE= "02";

    public final static String  CHARGE_STATUS_ONLINE= "01";

    public final static String  MSCX_USER_DATA= "mscx-user-data";
    public final static String  MSCX_AREA_CODE= "mscx-area-code";

    //资源是否下架（0 没有下架，1下架）
    public final static String  ONLINE_OR_NOT= "0";





}
