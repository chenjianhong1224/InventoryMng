package com.cjh.InventoryMng.entity;

import java.util.Date;

public class TMemberReduce {
    private Integer id;

    private String reduceDate;

    private Integer reduceAmount;

    private Integer memberId;

    private String creator;

    private Date updateTime;

    private Date createTime;

    private String updater;

    private String reduceItem;

    private Integer managerCostFlag;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReduceDate() {
        return reduceDate;
    }

    public void setReduceDate(String reduceDate) {
        this.reduceDate = reduceDate == null ? null : reduceDate.trim();
    }

    public Integer getReduceAmount() {
        return reduceAmount;
    }

    public void setReduceAmount(Integer reduceAmount) {
        this.reduceAmount = reduceAmount;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater == null ? null : updater.trim();
    }

    public String getReduceItem() {
        return reduceItem;
    }

    public void setReduceItem(String reduceItem) {
        this.reduceItem = reduceItem == null ? null : reduceItem.trim();
    }

    public Integer getManagerCostFlag() {
        return managerCostFlag;
    }

    public void setManagerCostFlag(Integer managerCostFlag) {
        this.managerCostFlag = managerCostFlag;
    }
}