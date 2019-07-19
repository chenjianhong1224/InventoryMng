package com.cjh.InventoryMng.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.TStockInfo;
import com.cjh.InventoryMng.entity.TStockInfoExample;
import com.cjh.InventoryMng.entity.VStockInfo;
import com.cjh.InventoryMng.mapper.CustomQueryMapper;
import com.cjh.InventoryMng.mapper.TGoodsInfoMapper;
import com.cjh.InventoryMng.mapper.TStockInfoMapper;
import com.cjh.InventoryMng.service.StockInfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
@Transactional
public class StockInfoServiceImpl implements StockInfoService {

	@Autowired
	private TStockInfoMapper tStockMapper;

	@Autowired
	private CustomQueryMapper customQueryMapper;

	@Autowired
	private TGoodsInfoMapper tGoodsInfoMapper;

	@Override
	public Page<VStockInfo> queryStockInfo(String goodsName, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return customQueryMapper.queryStockInfoByGoodsName(goodsName);
	}

	@Override
	public boolean newStockInfo(String opId, int goodsId, int count) {
		TGoodsInfo goods = tGoodsInfoMapper.selectByPrimaryKey(goodsId);
		TStockInfo record = new TStockInfo();
		record.setCount(count);
		record.setGoodId(goodsId);
		record.setStatus(1);
		tStockMapper.insert(record);
		return true;
	}

	@Override
	public TStockInfo queryStockInfoByGoodsId(int goodsId) {
		TStockInfoExample example = new TStockInfoExample();
		example.createCriteria().andGoodIdEqualTo(goodsId).andStatusEqualTo(1);
		Page<TStockInfo> list = tStockMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}
		return list.get(0);
	}

}
