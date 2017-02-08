package com.digitalchina.domain;

import java.util.Date;

public class OrderInfo {
    private Integer id;

    private String orderNum;

    private Integer orderStatus;

    private Date orderTime;

    private Double orderCashTotal;
    private Double havePay;;


    private String orderType;

    private String userId;

    private String area;

    private Date createdTime;

    private String createdBy;

    private Date udpatedTime;

    private String updatedBy;

    private String createUserId;

    private Double oneResourcePrice;

    private String orderUserName;

    private String payType;

    public Double getHavePay() {
        return havePay;
    }

    public void setHavePay(Double havePay) {
        this.havePay = havePay;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Double getOrderCashTotal() {
        return orderCashTotal;
    }

    public void setOrderCashTotal(Double orderCashTotal) {
        this.orderCashTotal = orderCashTotal;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Date getUdpatedTime() {
        return udpatedTime;
    }

    public void setUdpatedTime(Date udpatedTime) {
        this.udpatedTime = udpatedTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId == null ? null : createUserId.trim();
    }

    public Double getOneResourcePrice() {
        return oneResourcePrice;
    }

    public void setOneResourcePrice(Double oneResourcePrice) {
        this.oneResourcePrice = oneResourcePrice;
    }
}