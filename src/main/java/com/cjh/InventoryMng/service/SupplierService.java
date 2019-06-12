package com.cjh.InventoryMng.service;

import java.util.List;

import com.cjh.InventoryMng.entity.TSupplier;
import com.cjh.InventoryMng.entity.VSuppplierBillInfo;
import com.github.pagehelper.Page;

public interface SupplierService {

	TSupplier getSupplier(Integer supplierId);

	List<TSupplier> getAllSupplier();

	Page<VSuppplierBillInfo> querySupplierBill(String suppilerId, String beginDate, String endDate, Integer pageNo,
			Integer pageSize);
	
	Page<TSupplier> querySupplierByName(Integer page, Integer limit, String supplierName);
	
	boolean deleteSupplier(Integer id);

	boolean addSupplier(String suppliersName, String phone, String bossName, String address);

	boolean updateSupplier(Integer id, String suppliersName, String phone, String bossName, String address);
}
