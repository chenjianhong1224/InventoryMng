package com.cjh.InventoryMng.bean;

import java.io.Serializable;

public class MemberBean implements Serializable{

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	private Integer memberId;
	
	private String brandId;

	public String getBrandId() {
		return brandId;
	}

	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
}
