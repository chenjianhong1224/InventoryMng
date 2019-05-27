package com.cjh.InventoryMng.vo;

import java.io.Serializable;

public class GoodsOptionVO implements Serializable {
	
	private Integer id;
	
	private String goodName;

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

}
