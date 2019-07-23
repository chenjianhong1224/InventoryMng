package com.cjh.InventoryMng.service;

import com.cjh.InventoryMng.entity.TStockInfo;
import com.cjh.InventoryMng.entity.VStockInfo;
import com.github.pagehelper.Page;

public interface StockInfoService {
	
	Page<VStockInfo> queryStockInfo(String goodsName, int pageNo, int pageSize);
	
	boolean newStockInfo(String opId, int goodsId, int count);
	
	boolean modifyStockInfo(String opId, TStockInfo info);
	
	TStockInfo queryStockInfoByGoodsId(int goodsId);
	
	TStockInfo queryStockInfoById(int stockId);

}
