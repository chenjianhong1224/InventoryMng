package com.cjh.InventoryMng.vo;

import java.io.Serializable;

public class MemberBillInfoVO implements Serializable {

	private String brand;
	private String memberName;
	private String meituan;
	private String eleme;
	private String orderAmount;
	private String settleDate;
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
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
	public String getMeituan() {
		return meituan;
	}
	public void setMeituan(String meituan) {
		this.meituan = meituan;
	}
	public String getEleme() {
		return eleme;
	}
	public void setEleme(String eleme) {
		this.eleme = eleme;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
}
