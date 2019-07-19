package com.cjh.InventoryMng.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.TGoodsInfoExample;
import com.cjh.InventoryMng.mapper.CustomQueryMapper;
import com.cjh.InventoryMng.mapper.TGoodsInfoMapper;
import com.cjh.InventoryMng.service.GoodsService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TGoodsInfoMapper tGoodsInfoMapper;

	@Autowired
	private CustomQueryMapper customQueryMapper;

	@Override
	public boolean addGoods(String goodsName, Integer manufacturerId, Integer supplierId, String specifications,
			Integer purchasePrice, Integer memberPrice, Integer serviceId, Integer servicePrice, String creator) {
		TGoodsInfo record = new TGoodsInfo();
		record.setManufacturerId(manufacturerId);
		Date now = new Date();
		record.setCreateTime(now);
		record.setCreator(creator);
		record.setGoodName(goodsName);
		record.setMemberPrice(memberPrice);
		record.setPurchasePrice(purchasePrice);
		record.setSpecifications(specifications);
		record.setStatus(1);
		record.setServiceId(serviceId);
		record.setServicePrice(servicePrice);
		record.setSupplierId(supplierId);
		if (tGoodsInfoMapper.insert(record) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public Page<TGoodsInfo> queryAllEffectiveGoods(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		TGoodsInfoExample example = new TGoodsInfoExample();
		example.createCriteria().andStatusEqualTo(1);
		Page<TGoodsInfo> selectByExample = tGoodsInfoMapper.selectByExample(example);
		return selectByExample;
	}

	@Override
	public TGoodsInfo queryGoods(int goodsId) {
		return tGoodsInfoMapper.selectByPrimaryKey(goodsId);
	}

	@Override
	public Page<TGoodsInfo> queryEffectiveGoodsByManufacturerGoodsName(Integer manufacturerId, String goodsName,
			int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		if (StringUtils.isEmpty(goodsName)) {
			goodsName = null;
		}
		Page<TGoodsInfo> selectByExample = customQueryMapper.queryGoodsByManufacturerGoodName(manufacturerId,
				goodsName);
		return selectByExample;
	}

	@Override
	public Page<TGoodsInfo> queryMemberEffectiveGoodsByManufacturerGoodsName(int memberId, Integer manufacturerId,
			String goodsName, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		Page<TGoodsInfo> selectByExample = customQueryMapper
				.queryMemberEffectiveGoodsByManufacturerGoodsName(manufacturerId, memberId, goodsName);
		return selectByExample;
	}

	@Override
	public Page<TGoodsInfo> queryGoodsByName(int pageNo, int pageSize, String goodName) {
		PageHelper.startPage(pageNo, pageSize);
		TGoodsInfoExample example = new TGoodsInfoExample();
		example.createCriteria().andGoodNameLike("%" + goodName + "%");
		Page<TGoodsInfo> selectByExample = tGoodsInfoMapper.selectByExample(example);
		return selectByExample;
	}

	@Override
	public boolean deleteGoods(Integer id) {
		TGoodsInfoExample example = new TGoodsInfoExample();
		example.createCriteria().andIdEqualTo(id);
		TGoodsInfo record = new TGoodsInfo();
		record.setStatus(0);
		return tGoodsInfoMapper.updateByExampleSelective(record, example) == 1;
	}

	@Override
	public boolean updateGoods(Integer id, String goodsName, Integer manufacturerId, Integer supplierId,
			String specifications, Integer purchasePrice, Integer memberPrice, Integer serviceId, Integer servicePrice,
			String creator) {
		TGoodsInfo record = new TGoodsInfo();
		record.setId(id);
		record.setManufacturerId(manufacturerId);
		Date now = new Date();
		record.setUpdateTime(now);
		record.setUpdater(creator);
		record.setGoodName(goodsName);
		record.setMemberPrice(memberPrice);
		record.setPurchasePrice(purchasePrice);
		record.setSpecifications(specifications);
		record.setStatus(1);
		record.setServiceId(serviceId);
		record.setServicePrice(servicePrice);
		record.setSupplierId(supplierId);
		TGoodsInfoExample example = new TGoodsInfoExample();
		example.createCriteria().andIdEqualTo(id);
		if (tGoodsInfoMapper.updateByExample(record, example) == 1) {
			return true;
		}
		return false;
	}

	@Override
	public List<TGoodsInfo> queryGoodsListBySupplier(int supplerId, int status) {
		TGoodsInfoExample example = new TGoodsInfoExample();
		example.createCriteria().andSupplierIdEqualTo(supplerId).andStatusEqualTo(status);
		return tGoodsInfoMapper.selectByExample(example);
	}

}
