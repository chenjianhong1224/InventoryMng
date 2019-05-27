package com.cjh.InventoryMng.entity;

public class VMemberBillInfo {
	
	private Integer memberId; 
	
	private String memberName;
	
	private String brandId;
	
	private String brandName;
	
	private Integer meituanProfit;
	
	private Integer elemeProfit;
	
	private String settleDate;
	
	private Integer orderAmount;

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
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

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public Integer getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Integer orderAmount) {
		this.orderAmount = orderAmount;
	}
}
