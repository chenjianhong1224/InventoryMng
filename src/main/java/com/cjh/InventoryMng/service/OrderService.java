package com.cjh.InventoryMng.service;

import java.util.Date;
import java.util.List;

import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.TOrderInfo;
import com.cjh.InventoryMng.exception.BusinessException;
import com.github.pagehelper.Page;

public interface OrderService {

	int order(Integer memberId, Integer goodId, Integer buyNum, String orderDate, Date orderTime)
			throws BusinessException;

	boolean modifyOrder(Integer goodId, Integer buyNum);

	boolean deleteOrder(Integer goodId);

	Page<TOrderInfo> queryEffectiveOrders(String beginOrderDate, String endOrderDate, int pageNo, int pageSize);

	Page<TOrderInfo> queryEffectiveOrders(List<Integer> memberId, String beginOrderDate, String endOrderDate,
			int pageNo, int pageSize);

	Page<TOrderInfo> queryEffectiveOrders(Integer memberId, String orderDate, int pageNo, int pageSize);

	Page<TOrderInfo> queryEffectiveOrdersByMonth(Integer memberId, String month, int pageNo, int pageSize);

	Page<TOrderInfo> querySupplierOrdersByPeriod(Integer supplierId, String month, int pageNo, int pageSize);

	Page<TOrderInfo> queryEffectiveOrders(Integer memberId, String beginOrderDate, String endOrderDate, int pageNo,
			int pageSize);

	Page<TOrderInfo> queryEffectiveOrders(Integer memberId, String beginOrderDate, String endOrderDate);

	int queryEffectiveOrderGoodNum(Integer memberId, String orderDate, Integer goodId);
	
	Page<TGoodsInfo> queryMemberAvailableGoods(Integer memberId, int pageNo, int pageSize);

}
