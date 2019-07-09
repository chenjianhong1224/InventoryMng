package com.cjh.InventoryMng.entity;

import java.util.Date;

public class TAccountRecord {
    private Integer id;

    private Date createTime;

    private String createStaff;

    private String type;

    private Integer amount;

    private String theDate;

    private String applyDesc;

    private String recordUserId;

    private Integer status;

    private String why;

    private String file1Name;

    private String file2Name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateStaff() {
        return createStaff;
    }

    public void setCreateStaff(String createStaff) {
        this.createStaff = createStaff == null ? null : createStaff.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTheDate() {
        return theDate;
    }

    public void setTheDate(String theDate) {
        this.theDate = theDate == null ? null : theDate.trim();
    }

    public String getApplyDesc() {
        return applyDesc;
    }

    public void setApplyDesc(String applyDesc) {
        this.applyDesc = applyDesc == null ? null : applyDesc.trim();
    }

    public String getRecordUserId() {
        return recordUserId;
    }

    public void setRecordUserId(String recordUserId) {
        this.recordUserId = recordUserId == null ? null : recordUserId.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getWhy() {
        return why;
    }

    public void setWhy(String why) {
        this.why = why == null ? null : why.trim();
    }

    public String getFile1Name() {
        return file1Name;
    }

    public void setFile1Name(String file1Name) {
        this.file1Name = file1Name == null ? null : file1Name.trim();
    }

    public String getFile2Name() {
        return file2Name;
    }

    public void setFile2Name(String file2Name) {
        this.file2Name = file2Name == null ? null : file2Name.trim();
    }
}