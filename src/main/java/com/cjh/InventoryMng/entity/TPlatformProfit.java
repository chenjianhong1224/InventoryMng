package com.cjh.InventoryMng.entity;

public class TPlatformProfit {
    private Integer id;

    private Integer memberId;

    private String settleDate;

    private Integer meituanProfit;

    private Integer elemeProfit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate == null ? null : settleDate.trim();
    }

    public Integer getMeituanProfit() {
        return meituanProfit;
    }

    public void setMeituanProfit(Integer meituanProfit) {
        this.meituanProfit = meituanProfit;
    }

    public Integer getElemeProfit() {
        return elemeProfit;
    }

    public void setElemeProfit(Integer elemeProfit) {
        this.elemeProfit = elemeProfit;
    }
}