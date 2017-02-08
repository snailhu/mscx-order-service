package com.digitalchina.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Snail on 2016/11/30.
 */
public class OrderDto {

    String  userId;
    String area;
    Integer resouce_id;
    //资源类型
    String resource_type;
    String resouece_name;
    //套餐快照id
    Integer chare_rule_id;
    //套餐快照名
    String charge_rule_name;
    //套餐快照描述
    String charge_rule_des;
    //套餐快照类型
    Integer charge_rule_type;
    //套餐单价快照
    double item_cash;
    //订购数量
    Integer item_number;
    //订单类型
    String order_type;
    //订购时长（按月）
    Integer delayTime;
    //默认使用次数
    Integer defalutTime;




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



    public String getResource_type() {
        return resource_type;
    }

    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    public String getResouece_name() {
        return resouece_name;
    }

    public void setResouece_name(String resouece_name) {
        this.resouece_name = resouece_name;
    }

    public Integer getResouce_id() {
        return resouce_id;
    }

    public void setResouce_id(Integer resouce_id) {
        this.resouce_id = resouce_id;
    }

    public Integer getChare_rule_id() {
        return chare_rule_id;
    }

    public void setChare_rule_id(Integer chare_rule_id) {
        this.chare_rule_id = chare_rule_id;
    }

    public String getCharge_rule_name() {
        return charge_rule_name;
    }

    public void setCharge_rule_name(String charge_rule_name) {
        this.charge_rule_name = charge_rule_name;
    }

    public String getCharge_rule_des() {
        return charge_rule_des;
    }

    public void setCharge_rule_des(String charge_rule_des) {
        this.charge_rule_des = charge_rule_des;
    }

    public Integer getCharge_rule_type() {
        return charge_rule_type;
    }

    public void setCharge_rule_type(Integer charge_rule_type) {
        this.charge_rule_type = charge_rule_type;
    }

    public Integer getItem_number() {
        return item_number;
    }

    public void setItem_number(Integer item_number) {
        this.item_number = item_number;
    }

    public Integer getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Integer delayTime) {
        this.delayTime = delayTime;
    }

    public Integer getDefalutTime() {
        return defalutTime;
    }

    public void setDefalutTime(Integer defalutTime) {
        this.defalutTime = defalutTime;
    }

    public double getItem_cash() {
        return item_cash;
    }

    public void setItem_cash(double item_cash) {
        this.item_cash = item_cash;
    }



    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }
}
