package com.cjh.InventoryMng.service;

import java.util.Date;

import com.cjh.InventoryMng.exception.BusinessException;

public interface FinancialService {

	boolean newMemberOrder(String operator, Integer memberId, String memberName, Integer goodId, Integer buyNum,
			String orderDate) throws BusinessException, Exception;

	boolean modifyOrder(String operator, Integer id, Integer purchasePrice, Integer buyNum, Integer memberPrice,
			Integer servicePrice, String orderDate) throws BusinessException, Exception;
}
