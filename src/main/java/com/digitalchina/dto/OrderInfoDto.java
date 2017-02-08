package com.digitalchina.dto;

import com.digitalchina.domain.OrderDetail;

import java.util.List;

/**
 * Created by Snail on 2016/11/29.
 */
public class OrderInfoDto {
    String orderNum;
    String orderTime;
    double orderCash;
    double havePay;
    String orderType;
    String orderStatus;
    String orderUserName;
    String payWay;
    List<OrderDetail> sourceDetail;


    public double getHavePay() {
        return havePay;
    }

    public void setHavePay(double havePay) {
        this.havePay = havePay;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }



    public List<OrderDetail> getSourceDetail() {
        return sourceDetail;
    }

    public void setSourceDetail(List<OrderDetail> sourceDetail) {
        this.sourceDetail = sourceDetail;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getOrderCash() {
        return orderCash;
    }

    public void setOrderCash(double orderCash) {
        this.orderCash = orderCash;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


}
