package com.cjh.InventoryMng.entity;

import java.util.Date;

public class TFinanicalOpLog {
    private Integer id;

    private String opDesc;

    private String operator;

    private Date opTime;

    private Integer opRecordId;

    private Integer opType;

    private String opDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpDesc() {
        return opDesc;
    }

    public void setOpDesc(String opDesc) {
        this.opDesc = opDesc == null ? null : opDesc.trim();
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public Integer getOpRecordId() {
        return opRecordId;
    }

    public void setOpRecordId(Integer opRecordId) {
        this.opRecordId = opRecordId;
    }

    public Integer getOpType() {
        return opType;
    }

    public void setOpType(Integer opType) {
        this.opType = opType;
    }

    public String getOpDate() {
        return opDate;
    }

    public void setOpDate(String opDate) {
        this.opDate = opDate == null ? null : opDate.trim();
    }
}