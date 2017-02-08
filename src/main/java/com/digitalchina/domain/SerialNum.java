package com.digitalchina.domain;

import java.util.Date;

public class SerialNum {
    private Integer id;

    private Long version;

    private Long numSerial;

    private String area;

    private Date createdTime;

    private String createdBy;

    private Date udpatedTime;

    private String updatedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getNumSerial() {
        return numSerial;
    }

    public void setNumSerial(Long numSerial) {
        this.numSerial = numSerial;
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
}