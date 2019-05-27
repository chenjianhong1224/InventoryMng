package com.cjh.InventoryMng.service;

import java.util.List;

import com.cjh.InventoryMng.entity.TSysParam;


public interface SysParaService {
	
	List<TSysParam> getAllBrand();
	
	List<TSysParam> getAllManufacturer();
	
	String getBrandName(String brandId);
	
	String getManufacturerName(String manufacturerId);
	
	String getBrandId(String brandName);

}
