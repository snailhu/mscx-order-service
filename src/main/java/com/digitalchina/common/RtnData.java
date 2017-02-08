package com.digitalchina.common;


public class RtnData {

    private String code = "000000";
    private String message = "success";
    private String status="OK";
    private Object result;

    public RtnData(String code, String msg, String status,Object result) {
        this.code = code;
        this.message = msg;
        this.status = status;
        this.result = result;
    }
    public RtnData(String status) {
        this.status = status;

    }


    public RtnData(Object result) {
        this.result = result;
    }


    public RtnData() {
        this.code = code;
        this.message = message;
        this.status = status;

    }
    public static RtnData fail(Object result, String code, String message) {
        RtnData rtnData = new RtnData();
        rtnData.setCode(code);
        rtnData.setMessage(message);
        rtnData.setStatus("ERROR");
        rtnData.setResult(result);
        return rtnData;
    }
    public static RtnData ok(Object result, String message) {
        RtnData rtnData = new RtnData();
        rtnData.setCode("000000");
        rtnData.setStatus("OK");
        rtnData.setMessage(message);
        rtnData.setResult(result);
        return rtnData;
    }

    public static RtnData fail(Object result, String message) {
        RtnData rtnData = new RtnData();
        rtnData.setCode("999999");
        rtnData.setStatus("ERROR");
        rtnData.setMessage(message);
        rtnData.setResult(result);
        return rtnData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
