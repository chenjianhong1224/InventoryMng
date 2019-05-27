package com.cjh.InventoryMng.entity;

public class VMemberSupplierMap {
	
	private String memberName;
	
	private String supplierName;
	
	private Integer supplierId;
	
	private Integer memberId;
	
	private Integer groupMapId;
	
	private boolean checked;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getGroupMapId() {
		return groupMapId;
	}

	public void setGroupMapId(Integer groupMapId) {
		this.groupMapId = groupMapId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

}
