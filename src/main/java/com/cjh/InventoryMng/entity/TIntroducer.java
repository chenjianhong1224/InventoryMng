package com.cjh.InventoryMng.entity;

public class TIntroducer {
    private Integer id;

    private String introducer;

    private String custIp;

    private String dateTime;

    private String dDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer == null ? null : introducer.trim();
    }

    public String getCustIp() {
        return custIp;
    }

    public void setCustIp(String custIp) {
        this.custIp = custIp == null ? null : custIp.trim();
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime == null ? null : dateTime.trim();
    }

    public String getdDate() {
        return dDate;
    }

    public void setdDate(String dDate) {
        this.dDate = dDate == null ? null : dDate.trim();
    }
}