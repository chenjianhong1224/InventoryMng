package com.cjh.InventoryMng.service;

import java.text.ParseException;
import java.util.Date;

import com.cjh.InventoryMng.entity.TAccountRecord;
import com.cjh.InventoryMng.entity.TAccountRecordWithBLOBs;
import com.cjh.InventoryMng.entity.TCostRecord;
import com.cjh.InventoryMng.exception.BusinessException;
import com.github.pagehelper.Page;

public interface FinancialService {

	boolean newMemberOrder(String operator, Integer memberId, String memberName, Integer goodId, double buyNum,
			String orderDate) throws BusinessException, Exception;

	boolean modifyOrder(String operator, Integer id, Integer purchasePrice, double buyNum, Integer memberPrice,
			Integer servicePrice, String orderDate) throws BusinessException, Exception;

	boolean newMemberReduce(String creator, Integer memberId, String memberName, Integer reduceAmount,
			String reduceDate, String reduceItem);

	boolean modifyMemberReduce(String updater, Integer reduceId, String memberName, Integer reduceAmount,
			String reduceDate, String reduceItem);

	Page<TAccountRecord> queryTAccountRecord(String beginDate, String endDate, String desc, String userId, int pageNo,
			int pageSize) throws ParseException;

	Page<TAccountRecord> queryTAccountRecord(String beginDate, String endDate, String desc, int pageNo, int pageSize)
			throws ParseException;

	TAccountRecordWithBLOBs queryTAccountRecord(Integer id);

	boolean approveAccountRecord(Integer id, String approveUserId);

	boolean rejectAccountRecord(Integer id, String approveUserId, String why);

	boolean newTAccountRecord(String creator, String theDate, String type, String desc, Integer amount,
			String file1Name, byte[] file1, String file2Name, byte[] file2);

	Page<TCostRecord> queryTCostRecord(String beginDate, String endDate, String type, int pageNo, int pageSize)
			throws ParseException;

	boolean newCost(String creator, String type, String costDesc, Integer amount, String costDate);

	boolean modifyCost(int id, String updater, String type, String costDesc, Integer amount, String costDate);
}
