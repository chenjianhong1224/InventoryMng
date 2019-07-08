package com.cjh.InventoryMng.service;

import java.util.List;

import com.cjh.InventoryMng.bean.EmailBean;
import com.cjh.InventoryMng.entity.TSysParam;


public interface SysParaService {
	
	List<TSysParam> getAllBrand();
	
	List<TSysParam> getAllManufacturer();
	
	List<TSysParam> getAllAccountType();
	
	String getBrandName(String brandId);
	
	String getAccountRecordTypeName(String typeId);
	
	String getManufacturerName(String manufacturerId);
	
	String getBrandId(String brandName);

	List<String> getOrderEmailAddress();
	
	EmailBean getSysEmail();
}
