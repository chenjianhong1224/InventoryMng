package com.cjh.InventoryMng.vo;

import java.io.Serializable;
import java.util.Date;

import com.cjh.InventoryMng.annotation.ExcelAnnotation;

/**
 * @author admin
 *
 */
public class MemberOrderInfoVO implements Serializable {

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

	public String getSpecfications() {
		return specfications;
	}

	public void setSpecfications(String specfications) {
		this.specfications = specfications;
	}

	public String getMemberPrice() {
		return memberPrice;
	}

	public void setMemberPrice(String memberPrice) {
		this.memberPrice = memberPrice;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
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


	/**
	 * 
	 */
	private static final long serialVersionUID = 52765846537944112L;

	@ExcelAnnotation(TitleName = "ID", Order = 0)
	private Integer id;

	@ExcelAnnotation(TitleName = "商品名称", Order = 1)
	private String goodName;

	@ExcelAnnotation(TitleName = "厂家", Order = 2)
	private String manufacturerName;

	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}


	@ExcelAnnotation(TitleName = "规格", Order = 3)
	private String specfications;

	@ExcelAnnotation(TitleName = "单价", Order = 4)
	private String memberPrice;

	private Date orderTime;
	
	@ExcelAnnotation(TitleName = "订购日期", Order = 5)
	private String orderDate;

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	@ExcelAnnotation(TitleName = "订购数量(件)", Order = 6)
	private Double num;
}
