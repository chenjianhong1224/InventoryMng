package com.cjh.InventoryMng.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cjh.InventoryMng.entity.TGroupMap;
import com.cjh.InventoryMng.entity.TGroupMapExample;
import com.cjh.InventoryMng.entity.TMemberInfo;
import com.cjh.InventoryMng.entity.TMemberInfoExample;
import com.cjh.InventoryMng.entity.TMemberInfoExample.Criteria;
import com.cjh.InventoryMng.entity.TSupplier;
import com.cjh.InventoryMng.entity.TSupplierExample;
import com.cjh.InventoryMng.entity.VSuppplierBillInfo;
import com.cjh.InventoryMng.mapper.CustomQueryMapper;
import com.cjh.InventoryMng.mapper.TGroupMapMapper;
import com.cjh.InventoryMng.mapper.TMemberInfoMapper;
import com.cjh.InventoryMng.mapper.TSupplierMapper;
import com.cjh.InventoryMng.service.SupplierService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Service
public class SupplierServiceImpl implements SupplierService {

	@Autowired
	private TSupplierMapper tSupplierMapper;

	@Autowired
	private CustomQueryMapper customQueryMapper;

	@Autowired
	private TGroupMapMapper tGroupMapMapper;

	@Autowired
	private TMemberInfoMapper tMemberInfoMapper;

	@Override
	public TSupplier getSupplier(Integer supplierId) {
		return tSupplierMapper.selectByPrimaryKey(supplierId);
	}

	@Override
	public List<TSupplier> getAllSupplier() {
		TSupplierExample example = new TSupplierExample();
		return tSupplierMapper.selectByExample(example);
	}

	@Override
	public Page<VSuppplierBillInfo> querySupplierBill(String supplierId, String beginDate, String endDate,
			String brandId, String memberName, Integer pageNo, Integer pageSize) {
		TMemberInfoExample example = new TMemberInfoExample();
		Criteria c = example.createCriteria();
		if (!StringUtils.isEmpty(brandId)) {
			c.andBrandEqualTo(brandId);
		}
		if (!StringUtils.isEmpty(memberName)) {
			c.andMemberNameLike("%" + memberName + "%");
		}
		Page<TMemberInfo> memberList = tMemberInfoMapper.selectByExample(example);
		List<Integer> memberIds = Lists.newArrayList();
		for(TMemberInfo tMemberInfo : memberList){
			memberIds.add(tMemberInfo.getId());
		}
		Map<String, Object> param = Maps.newHashMap();
		param.put("supplierId", supplierId);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		param.put("memberIds", memberIds);
		param.put("pageNo", pageNo);
		param.put("pageSize", pageSize);
		return customQueryMapper.querySupplierBill(param);
	}

	@Override
	public Page<TSupplier> querySupplierByName(Integer page, Integer limit, String supplierName) {
		PageHelper.startPage(page, limit);
		TSupplierExample example = new TSupplierExample();
		example.createCriteria().andSupplierNameLike("%" + supplierName + "%");
		return tSupplierMapper.selectByExample(example);
	}

	@Override
	public boolean deleteSupplier(Integer id) {
		TSupplier record = new TSupplier();
		record.setStatus(0);
		TSupplierExample example = new TSupplierExample();
		example.createCriteria().andIdEqualTo(id);
		if (tSupplierMapper.updateByExampleSelective(record, example) == 1) {
			TGroupMap groupRecord = new TGroupMap();
			groupRecord.setStatus(0);
			TGroupMapExample groupExample = new TGroupMapExample();
			groupExample.createCriteria().andSupplierIdEqualTo(id);
			tGroupMapMapper.updateByExampleSelective(groupRecord, groupExample);
		}
		return true;
	}

	@Override
	public boolean addSupplier(String suppliersName, String phone, String bossName, String address) {
		TSupplier record = new TSupplier();
		record.setBossName(bossName);
		record.setAddress(address);
		record.setPhone(phone);
		record.setStatus(1);
		record.setSupplierName(suppliersName);
		return tSupplierMapper.insert(record) == 1;
	}

	@Override
	public boolean updateSupplier(Integer id, String suppliersName, String phone, String bossName, String address) {
		TSupplier record = new TSupplier();
		record.setAddress(address);
		record.setBossName(bossName);
		record.setPhone(phone);
		record.setSupplierName(suppliersName);
		record.setStatus(1);
		TSupplierExample example = new TSupplierExample();
		example.createCriteria().andIdEqualTo(id);
		return tSupplierMapper.updateByExampleSelective(record, example) == 1;
	}

}
