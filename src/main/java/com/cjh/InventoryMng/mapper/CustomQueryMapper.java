package com.cjh.InventoryMng.mapper;

import org.apache.ibatis.annotations.Param;

import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.VMemberBillInfo;
import com.cjh.InventoryMng.entity.VRoleResourceInfo;
import com.cjh.InventoryMng.entity.VSuppplierBillInfo;
import com.cjh.InventoryMng.entity.VMemberSupplierMap;
import com.github.pagehelper.Page;

public interface CustomQueryMapper {

	Page<TGoodsInfo> queryGoodsByManufacturerGoodName(@Param("manufacturerId") Integer manufacturerId,
			@Param("goodName") String goodName);

	Page<TGoodsInfo> queryMemberEffectiveGoodsByManufacturerGoodsName(@Param("manufacturerId") Integer manufacturerId,
			@Param("memberId") Integer memberId, @Param("goodName") String goodName);

	Page<VMemberBillInfo> queryMemberBill(@Param("brandId") String brandId, @Param("memberId") Integer memberId,
			@Param("beginDate") String beginDate, @Param("endDate") String endDate);

	Page<VSuppplierBillInfo> querySupplierBill(@Param("supplierId") String supplierId,
			@Param("beginDate") String beginDate, @Param("endDate") String endDate);

	Page<VRoleResourceInfo> queryResourceOfRole(@Param("roleId") Integer roleId);
	
	Page<VMemberSupplierMap> querySupplierOfMember(@Param("memberId") Integer memberId);
	
	Page<TGoodsInfo> queryMemberAvailableGoods(@Param("memberId") Integer memberId);
}
