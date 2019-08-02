package com.cjh.InventoryMng.vo;

import java.io.Serializable;

public class CompanyProfitReportVO implements Serializable {

	public String getGoodsProfit() {
		return goodsProfit;
	}

	public void setGoodsProfit(String goodsProfit) {
		this.goodsProfit = goodsProfit;
	}

	public String getManagerProfit() {
		return managerProfit;
	}

	public void setManagerProfit(String managerProfit) {
		this.managerProfit = managerProfit;
	}

	public String getFreightCost() {
		return freightCost;
	}

	public void setFreightCost(String freightCost) {
		this.freightCost = freightCost;
	}

	public String getAllCost() {
		return allCost;
	}

	public void setAllCost(String allCost) {
		this.allCost = allCost;
	}

	public String getApprovedAccount() {
		return approvedAccount;
	}

	public void setApprovedAccount(String approvedAccount) {
		this.approvedAccount = approvedAccount;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	private String goodsProfit;

	private String managerProfit;

	private String freightCost;

	private String allCost;

	private String approvedAccount;

	public String getSalaryCost() {
		return salaryCost;
	}

	public void setSalaryCost(String salaryCost) {
		this.salaryCost = salaryCost;
	}

	public String getTuiguangReduce() {
		return tuiguangReduce;
	}

	public void setTuiguangReduce(String tuiguangReduce) {
		this.tuiguangReduce = tuiguangReduce;
	}

	private String month;

	private String salaryCost;

	private String tuiguangReduce;
}
