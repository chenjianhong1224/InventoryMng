package com.cjh.InventoryMng.entity;

public class TCompanyProfit {
    private Integer id;

    private Integer goodsProfit;

    private Integer managerProfit;

    private Integer freightCost;

    private Integer allCost;

    private Integer approvedAccount;

    private String month;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsProfit() {
        return goodsProfit;
    }

    public void setGoodsProfit(Integer goodsProfit) {
        this.goodsProfit = goodsProfit;
    }

    public Integer getManagerProfit() {
        return managerProfit;
    }

    public void setManagerProfit(Integer managerProfit) {
        this.managerProfit = managerProfit;
    }

    public Integer getFreightCost() {
        return freightCost;
    }

    public void setFreightCost(Integer freightCost) {
        this.freightCost = freightCost;
    }

    public Integer getAllCost() {
        return allCost;
    }

    public void setAllCost(Integer allCost) {
        this.allCost = allCost;
    }

    public Integer getApprovedAccount() {
        return approvedAccount;
    }

    public void setApprovedAccount(Integer approvedAccount) {
        this.approvedAccount = approvedAccount;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }
}