package com.digitalchina.domain;

import java.util.Date;

public class OrderDetail {
    private Integer id;

    private Integer orderId;

    private Integer resourceId;

    private String resourceType;

    private String resourceName;

    private Integer chargeRuleId;

    private String chargeRuleName;

    private String chargeRuleDes;

    private String chargeRuleType;

    private Double itemCash;

    private Integer itemNumber;

    private Double itemCashTotal;

    private String area;

    private Date createdTime;

    private String createdBy;

    private Date udpatedTime;

    private String updatedBy;

    private Integer defaulTime;

    private Integer resourceDelayTime;

    private boolean isFree;
    //付款类型
    private String  payType;

    private String orderUserId;

    private String orderUserName;

    private String createUserId;

    private String orderNum;

    private String sourceJson;

    private String unitPrice;

    public String getSourceJson() {
        return sourceJson;
    }

    public void setSourceJson(String sourceJson) {
        this.sourceJson = sourceJson;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getOrderUserId() {
        return orderUserId;
    }

    public void setOrderUserId(String orderUserId) {
        this.orderUserId = orderUserId;
    }

    public String getOrderUserName() {
        return orderUserName;
    }

    public void setOrderUserName(String orderUserName) {
        this.orderUserName = orderUserName;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType == null ? null : resourceType.trim();
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName == null ? null : resourceName.trim();
    }

    public Integer getChargeRuleId() {
        return chargeRuleId;
    }

    public void setChargeRuleId(Integer chargeRuleId) {
        this.chargeRuleId = chargeRuleId;
    }

    public String getChargeRuleName() {
        return chargeRuleName;
    }

    public void setChargeRuleName(String chargeRuleName) {
        this.chargeRuleName = chargeRuleName == null ? null : chargeRuleName.trim();
    }

    public String getChargeRuleDes() {
        return chargeRuleDes;
    }

    public void setChargeRuleDes(String chargeRuleDes) {
        this.chargeRuleDes = chargeRuleDes == null ? null : chargeRuleDes.trim();
    }

    public String getChargeRuleType() {
        return chargeRuleType;
    }

    public void setChargeRuleType(String chargeRuleType) {
        this.chargeRuleType = chargeRuleType;
    }

    public Double getItemCash() {
        return itemCash;
    }

    public void setItemCash(Double itemCash) {
        this.itemCash = itemCash;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public Double getItemCashTotal() {
        return itemCashTotal;
    }

    public void setItemCashTotal(Double itemCashTotal) {
        this.itemCashTotal = itemCashTotal;
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

    public Integer getDefaulTime() {
        return defaulTime;
    }

    public void setDefaulTime(Integer defaulTime) {
        this.defaulTime = defaulTime;
    }

    public Integer getResourceDelayTime() {
        return resourceDelayTime;
    }

    public void setResourceDelayTime(Integer resourceDelayTime) {
        this.resourceDelayTime = resourceDelayTime;
    }
}