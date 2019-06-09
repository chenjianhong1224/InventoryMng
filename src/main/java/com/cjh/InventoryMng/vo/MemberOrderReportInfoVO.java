package com.cjh.InventoryMng.vo;

import java.io.Serializable;
import java.util.Date;

import com.cjh.InventoryMng.annotation.ExcelAnnotation;

/**
 * @author admin
 *
 */
public class MemberOrderReportInfoVO implements Serializable {

	private String servicePrice;
	
	private Integer memberId;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(String memberPrice) {
		this.memberPrice = memberPrice;
	}

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

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getNum() {
		return num;
	}

	public void setNum(Double num) {
		this.num = num;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 52765846537944112L;

	@ExcelAnnotation(TitleName = "ID", Order = 0)
	private Integer id;

	@ExcelAnnotation(TitleName = "加盟商", Order = 1)
	private String memberName;
	
	@ExcelAnnotation(TitleName = "商品名称", Order = 2)
	private String goodName;

	@ExcelAnnotation(TitleName = "品牌", Order = 3)
	private String brand;

	@ExcelAnnotation(TitleName = "供应商", Order = 4)
	private String supplierName;

	@ExcelAnnotation(TitleName = "加盟价格", Order = 5)
	private String memberPrice;

	@ExcelAnnotation(TitleName = "采购价格", Order = 6)
	private String purchasePrice;

	@ExcelAnnotation(TitleName = "订购日期", Order = 7)
	private String orderDate;
	
	@ExcelAnnotation(TitleName = "厂家", Order = 8)
	private String manufactor;

	public String getManufactor() {
		return manufactor;
	}

	public void setManufactor(String manufactor) {
		this.manufactor = manufactor;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public String getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(String servicePrice) {
		this.servicePrice = servicePrice;
	}

	@ExcelAnnotation(TitleName = "订购数量(件)", Order = 6)
	private Double num;
}
