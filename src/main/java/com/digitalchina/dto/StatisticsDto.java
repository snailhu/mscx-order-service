package com.digitalchina.dto;

/**
 * Created by Snail on 2016/12/30.
 */
public class StatisticsDto {
    Integer resourceId;
    //用户访问两
    Integer applyUserCount;
    //被申请次数
    Integer applyCount;
    //资源类型
    String resourceType;
    //区域代码
    String area;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getApplyUserCount() {
        return applyUserCount;
    }

    public void setApplyUserCount(Integer applyUserCount) {
        this.applyUserCount = applyUserCount;
    }

    public Integer getApplyCount() {
        return applyCount;
    }

    public void setApplyCount(Integer applyCount) {
        this.applyCount = applyCount;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
}
