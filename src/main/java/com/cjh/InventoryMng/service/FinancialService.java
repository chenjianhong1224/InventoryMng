package com.cjh.InventoryMng.service;

import java.util.Date;

import com.cjh.InventoryMng.exception.BusinessException;

public interface FinancialService {

	boolean newMemberOrder(String operator, Integer memberId, String memberName, Integer goodId, double buyNum,
			String orderDate) throws BusinessException, Exception;

	boolean modifyOrder(String operator, Integer id, Integer purchasePrice, double buyNum, Integer memberPrice,
			Integer servicePrice, String orderDate) throws BusinessException, Exception;

	boolean newMemberReduce(String creator, Integer memberId, String memberName, Integer reduceAmount,
			String reduceDate, String reduceItem);

	boolean modifyMemberReduce(String updater, Integer reduceId, String memberName, Integer reduceAmount,
			String reduceDate, String reduceItem);
}
