package com.cjh.InventoryMng.vo;

import java.io.Serializable;

public class PlatformProfitVO implements Serializable {

	private String brand;

	private String memberName;

	private String settleDate;

	private String meituanProfit;

	private String elemeProfit;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getMeituanProfit() {
		return meituanProfit;
	}

	public void setMeituanProfit(String meituanProfit) {
		this.meituanProfit = meituanProfit;
	}

	public String getElemeProfit() {
		return elemeProfit;
	}

	public void setElemeProfit(String elemeProfit) {
		this.elemeProfit = elemeProfit;
	}
}
