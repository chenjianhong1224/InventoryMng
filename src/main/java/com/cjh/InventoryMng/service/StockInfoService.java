package com.cjh.InventoryMng.service;

import com.cjh.InventoryMng.entity.TStockInfo;
import com.cjh.InventoryMng.entity.VStockInfo;
import com.github.pagehelper.Page;

public interface StockInfoService {
	
	Page<VStockInfo> queryStockInfo(String goodsName, int pageNo, int pageSize);
	
	boolean newStockInfo(String opId, int goodsId, int count);
	
	TStockInfo queryStockInfoByGoodsId(int goodsId);

}
