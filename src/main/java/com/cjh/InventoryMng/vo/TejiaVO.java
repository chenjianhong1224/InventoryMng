package com.cjh.InventoryMng.vo;

import java.io.Serializable;

public class TejiaVO implements Serializable {

	private String orginPrice;
	
	private String specialOffer;

	public String getOrginPrice() {
		return orginPrice;
	}

	public void setOrginPrice(String orginPrice) {
		this.orginPrice = orginPrice;
	}

	public String getSpecialOffer() {
		return specialOffer;
	}

	public void setSpecialOffer(String specialOffer) {
		this.specialOffer = specialOffer;
	}
}
