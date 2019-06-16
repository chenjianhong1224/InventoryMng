package com.cjh.InventoryMng.vo;

import java.io.Serializable;

public class MemberReduceInfoVO implements Serializable {
	
	private int reduceId;
	
	private int memberId;
	
	private String memberName;
	
	private String reduceAmount;
	
	private String reduceDate;
	
	private String reduceItem;

	public int getReduceId() {
		return reduceId;
	}

	public void setReduceId(int reduceId) {
		this.reduceId = reduceId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getReduceAmount() {
		return reduceAmount;
	}

	public void setReduceAmount(String reduceAmount) {
		this.reduceAmount = reduceAmount;
	}

	public String getReduceDate() {
		return reduceDate;
	}

	public void setReduceDate(String reduceDate) {
		this.reduceDate = reduceDate;
	}

	public String getReduceItem() {
		return reduceItem;
	}

	public void setReduceItem(String reduceItem) {
		this.reduceItem = reduceItem;
	}

}
