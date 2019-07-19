package com.cjh.InventoryMng.service;

import java.util.List;

import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.github.pagehelper.Page;

public interface GoodsService {

	boolean addGoods(String goodsName, Integer manufacturerId, Integer supplierId, String specifications,
			Integer purchasePrice, Integer memberPrice, Integer serviceId, Integer servicePrice, String creator);

	boolean updateGoods(Integer id, String goodsName, Integer manufacturerId, Integer supplierId, String specifications,
			Integer purchasePrice, Integer memberPrice, Integer serviceId, Integer servicePrice, String creator);

	Page<TGoodsInfo> queryAllEffectiveGoods(int pageNo, int pageSize);

	Page<TGoodsInfo> queryGoodsByName(int pageNo, int pageSize, String goodName);

	Page<TGoodsInfo> queryMemberEffectiveGoodsByManufacturerGoodsName(int memberId, Integer manufacturerId,
			String goodsName, int pageNo, int pageSize);

	Page<TGoodsInfo> queryEffectiveGoodsByManufacturerGoodsName(Integer manufacturerId, String goodsName, int pageNo,
			int pageSize);

	TGoodsInfo queryGoods(int goodsId);

	boolean deleteGoods(Integer id);
	
	List<TGoodsInfo> queryGoodsListBySupplier(int supplerId, int status);

}
