package com.cjh.InventoryMng.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.TOrderInfo;
import com.cjh.InventoryMng.entity.TOrderInfoExample;
import com.cjh.InventoryMng.entity.VMemberOrderInfoOrderBy;
import com.cjh.InventoryMng.entity.VUseGoodsCount;
import com.cjh.InventoryMng.exception.BusinessException;
import com.cjh.InventoryMng.mapper.CustomQueryMapper;
import com.cjh.InventoryMng.mapper.TGoodsInfoMapper;
import com.cjh.InventoryMng.mapper.TOrderInfoMapper;
import com.cjh.InventoryMng.service.OrderService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Maps;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private TGoodsInfoMapper tGoodsInfoMapper;

	@Autowired
	private TOrderInfoMapper tOrderInfoMapper;

	@Autowired
	private CustomQueryMapper customQueryMapper;

	@Override
	public double order(Integer memberId, Integer goodId, double buyNum, String orderDate, Date orderTime)
			throws BusinessException {
		TGoodsInfo goodsInfo = tGoodsInfoMapper.selectByPrimaryKey(goodId);
		if (goodsInfo != null && goodsInfo.getStatus() == 1) {
			TOrderInfoExample example = new TOrderInfoExample();
			example.createCriteria().andGoodIdEqualTo(goodId).andMemberIdEqualTo(memberId)
					.andOrderDateEqualTo(orderDate);
			Page<TOrderInfo> orders = tOrderInfoMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(orders)) {
				if (buyNum <= 0) {
					throw new BusinessException("减少订购数失败");
				}
				TOrderInfo record = new TOrderInfo();
				record.setGoodId(goodId);
				record.setMemberId(memberId);
				record.setMemberPrice(goodsInfo.getMemberPrice());
				record.setNum(buyNum);
				record.setSupplierid(goodsInfo.getSupplierId());
				Date now = new Date();
				record.setOrderDate(orderDate);
				record.setOrderTime(now);
				record.setPurchasePrice(goodsInfo.getPurchasePrice()
						+ (goodsInfo.getServicePrice() == null ? 0 : goodsInfo.getServicePrice()));
				record.setStatus(1);
				if (goodsInfo.getServiceId() != null) {
					record.setServiceId(goodsInfo.getServiceId());
				}
				if (goodsInfo.getServicePrice() != null) {
					record.setServicePrice(goodsInfo.getServicePrice());
				}
				int insertSucc = tOrderInfoMapper.insert(record);
				return buyNum;
			} else {
				TOrderInfo tOrderInfo = orders.get(0);
				if (tOrderInfo.getStatus() == 0) {
					if (buyNum <= 0) {
						throw new BusinessException("减少订购数失败");
					}
					tOrderInfo.setStatus(1);
					tOrderInfo.setOrderTime(orderTime);
					tOrderInfo.setNum(buyNum);
					int updateSucc = tOrderInfoMapper.updateByPrimaryKey(tOrderInfo);
					return buyNum;
				} else {
					tOrderInfo.setOrderTime(orderTime);
					if (tOrderInfo.getNum() + buyNum < 0) {
						throw new BusinessException("减少订购数失败");
					}
					if (tOrderInfo.getNum() + buyNum == 0) {
						tOrderInfo.setStatus(0);
					}
					tOrderInfo.setNum(tOrderInfo.getNum() + buyNum);
					int updateSucc = tOrderInfoMapper.updateByPrimaryKey(tOrderInfo);
					return tOrderInfo.getNum();
				}
			}
		} else {
			throw new BusinessException("商品目前不可订购");
		}
	}

	@Override
	public boolean modifyOrder(Integer goodId, double buyNum) {
		if (buyNum <= 0) {
			return false;
		}
		TOrderInfo theOrder = tOrderInfoMapper.selectByPrimaryKey(goodId);
		if (theOrder == null) {
			return false;
		}
		String endDateTime = theOrder.getOrderDate() + "150000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		try {
			if (now.getTime() > sdf.parse(endDateTime).getTime()) {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		TOrderInfo record = new TOrderInfo();
		record.setGoodId(goodId);
		record.setNum(buyNum);
		record.setOrderTime(now);
		int updateSucc = tOrderInfoMapper.updateByPrimaryKeySelective(record);
		return updateSucc == 1;
	}

	@Override
	public boolean deleteOrder(Integer goodId) {
		TOrderInfo theOrder = tOrderInfoMapper.selectByPrimaryKey(goodId);
		if (theOrder == null) {
			return false;
		}
		String endDateTime = theOrder.getOrderDate() + "150000";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date now = new Date();
		try {
			if (now.getTime() > sdf.parse(endDateTime).getTime()) {
				return false;
			}
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		TOrderInfo record = new TOrderInfo();
		record.setGoodId(goodId);
		record.setStatus(0);
		int updateSucc = tOrderInfoMapper.updateByPrimaryKeySelective(record);
		return updateSucc == 1;
	}

	@Override
	public Page<TOrderInfo> queryEffectiveOrders(Integer memberId, String orderDate, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		TOrderInfoExample example = new TOrderInfoExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andOrderDateEqualTo(orderDate).andStatusEqualTo(1);
		Page<TOrderInfo> orders = tOrderInfoMapper.selectByExample(example);
		return orders;
	}

	@Override
	public Page<TOrderInfo> queryEffectiveOrders(Integer memberId, String beginOrderDate, String endOrderDate,
			int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		TOrderInfoExample example = new TOrderInfoExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andOrderDateGreaterThanOrEqualTo(beginOrderDate)
				.andOrderDateLessThanOrEqualTo(endOrderDate).andStatusEqualTo(1);
		Page<TOrderInfo> orders = tOrderInfoMapper.selectByExample(example);
		return orders;
	}

	@Override
	public Page<TOrderInfo> queryEffectiveOrders(Integer memberId, String beginOrderDate, String endOrderDate) {
		TOrderInfoExample example = new TOrderInfoExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andOrderDateGreaterThanOrEqualTo(beginOrderDate)
				.andOrderDateLessThanOrEqualTo(endOrderDate).andStatusEqualTo(1);
		Page<TOrderInfo> orders = tOrderInfoMapper.selectByExample(example);
		return orders;
	}

	@Override
	public Page<TOrderInfo> queryEffectiveOrdersByMonth(Integer memberId, String month, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		TOrderInfoExample example = new TOrderInfoExample();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			Date monthDate = sdf.parse(month);
			Calendar cal = Calendar.getInstance();
			cal.setTime(monthDate);
			cal.add(Calendar.MONTH, 1);
			example.createCriteria().andMemberIdEqualTo(memberId).andOrderTimeBetween(monthDate, cal.getTime())
					.andStatusEqualTo(1);
			Page<TOrderInfo> orders = tOrderInfoMapper.selectByExample(example);
			return orders;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Page<TOrderInfo> querySupplierOrdersByPeriod(Integer supplierId, String period, int pageNo, int pageSize) {
		String month = period.substring(0, 4);
		String beginDate = "";
		String endDate = "";
		if (period.endsWith("03")) {
			beginDate = month + "21";
			endDate = month + "01";
		} else if (period.endsWith("13")) {
			beginDate = month + "01";
			endDate = month + "11";
		} else if (period.endsWith("23")) {
			beginDate = month + "11";
			endDate = month + "21";
		} else {
			return null;
		}
		PageHelper.startPage(pageNo, pageSize);
		TOrderInfoExample example = new TOrderInfoExample();
		example.createCriteria().andSupplieridEqualTo(supplierId).andOrderDateGreaterThanOrEqualTo(beginDate)
				.andOrderDateLessThan(endDate).andStatusEqualTo(1);
		Page<TOrderInfo> orders = tOrderInfoMapper.selectByExample(example);
		return orders;
	}

	@Override
	public double queryEffectiveOrderGoodNum(Integer memberId, String orderDate, Integer goodId) {
		TOrderInfoExample example = new TOrderInfoExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andOrderDateEqualTo(orderDate).andStatusEqualTo(1)
				.andGoodIdEqualTo(goodId);
		Page<TOrderInfo> orders = tOrderInfoMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(orders)) {
			return 0;
		}
		double num = 0;
		for (TOrderInfo order : orders) {
			num += order.getNum();
		}
		return num;
	}

	@Override
	public Page<TOrderInfo> queryEffectiveOrders(String beginOrderDate, String endOrderDate, int pageNo, int pageSize) {
		TOrderInfoExample example = new TOrderInfoExample();
		example.createCriteria().andOrderDateGreaterThanOrEqualTo(beginOrderDate)
				.andOrderDateLessThanOrEqualTo(endOrderDate).andStatusEqualTo(1);
		PageHelper.startPage(pageNo, pageSize);
		Page<TOrderInfo> orders = tOrderInfoMapper.selectByExample(example);
		return orders;
	}

	@Override
	public Page<TOrderInfo> queryEffectiveOrders(List<Integer> memberId, String beginOrderDate, String endOrderDate,
			int pageNo, int pageSize) {
		TOrderInfoExample example = new TOrderInfoExample();
		example.createCriteria().andMemberIdIn(memberId).andOrderDateGreaterThanOrEqualTo(beginOrderDate)
				.andOrderDateLessThanOrEqualTo(endOrderDate).andStatusEqualTo(1);
		PageHelper.startPage(pageNo, pageSize);
		Page<TOrderInfo> orders = tOrderInfoMapper.selectByExample(example);
		return orders;
	}

	@Override
	public Page<TGoodsInfo> queryMemberAvailableGoods(Integer memberId, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return customQueryMapper.queryMemberAvailableGoods(memberId);
	}

	@Override
	public String getOrderContent(Integer memberId, String brandId, String orderDate) {
		Page<VMemberOrderInfoOrderBy> list = customQueryMapper.queryMemberOrderInfoOrderBy(memberId, brandId, orderDate);
		int lastMemberId = -1;
		String lastSupplierName = "";
		Map<String, String> sendContentMap = Maps.newHashMap();
		String emailContent = "";
		for (VMemberOrderInfoOrderBy vo : list) {
			if (!lastSupplierName.equals(vo.getSupplierName())) {
				if (!StringUtils.isEmpty(lastSupplierName)) {
					sendContentMap.put(lastSupplierName, emailContent);
				}
				emailContent = "";
				lastSupplierName = vo.getSupplierName();
			}
			if (lastMemberId != vo.getMemberId()) {
				emailContent += "\r\n" + vo.getMemberName() + "下单\r\n";
				emailContent += "地址：" + vo.getAddress() + "\r\n";
				lastMemberId = vo.getMemberId();
			}
			emailContent += vo.getGoodsName() + " " + vo.getNum() + "件\r\n";
		}
		sendContentMap.put(lastSupplierName, emailContent);
		String finalContent = "";
		for (String supplierName : sendContentMap.keySet()) {
			finalContent += "========\r\n" + supplierName + "\r\n" + sendContentMap.get(supplierName);
		}
		return finalContent;
	}

	@Override
	public Page<VUseGoodsCount> queryUseGoodsCount(Integer memberId, String beginDate, String endDate, String goodsName,
			int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return customQueryMapper.queryUseGoodsCount(memberId, beginDate, endDate, goodsName);
	}
}
