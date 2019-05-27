package com.cjh.InventoryMng.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cjh.InventoryMng.entity.TMemberInfo;
import com.cjh.InventoryMng.entity.VMemberBillInfo;
import com.cjh.InventoryMng.entity.VMemberSupplierMap;
import com.github.pagehelper.Page;

public interface MemberService {

	List<TMemberInfo> getAllEffectiveMember();

	List<TMemberInfo> getEffectiveMember(String brandId);

	TMemberInfo getMemberInfo(String userId);

	TMemberInfo getMemberInfo(Integer memberId);

	String getMemberBrand(Integer memberId);

	Page<VMemberBillInfo> getMemberBill(String brandId, Integer memberId, String beginDate, String endDate, int pageNo,
			int pageSize);

	Integer getMemberId(String memberName, String brandId);

	Page<TMemberInfo> getAllMember(String memberName, String brandId, int pageNo, int pageSize);

	boolean deleteMember(Integer id);

	boolean newMember(String memberName, String city, String province, String phone, String address, String brand,
			String accNo, String accName, Integer roleId);

	boolean updateMember(Integer id, String memberName, String city, String province, String phone, String address,
			String brand, String accNo, String accName, Integer roleId);

	Page<VMemberSupplierMap> querySupplierOfMember(Integer memberId, int pageNo, int pageSize);
	
	boolean mapSupplier(Integer memberId, Integer supplierId, boolean bindFlag);
}
