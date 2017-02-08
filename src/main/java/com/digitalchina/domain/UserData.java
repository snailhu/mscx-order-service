package com.digitalchina.domain;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * Created by liuyd on 2016/12/9.
 * 请求头中的 用户信息实体类
 */
public class UserData {
    private String userId;
    private String account;
    private String phone;
    private String name;
    private String enterprise;
    private String partner;
    private String area;
    //临时订单号
    private String temNum;

    public String getTemNum() {
        return temNum;
    }

    public void setTemNum(String temNum) {
        this.temNum = temNum;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getName() {
        try {
            if(!StringUtils.isEmpty(name)){
                return new String(Base64.decodeBase64(name),"UTF-8");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

}