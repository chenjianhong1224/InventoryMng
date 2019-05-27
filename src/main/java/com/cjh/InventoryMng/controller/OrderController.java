package com.cjh.InventoryMng.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.InventoryMng.bean.UserInfo;
import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.TOrderInfo;
import com.cjh.InventoryMng.exception.BusinessException;
import com.cjh.InventoryMng.service.GoodsService;
import com.cjh.InventoryMng.service.OrderService;
import com.cjh.InventoryMng.service.SysParaService;
import com.cjh.InventoryMng.utils.DateUtils;
import com.cjh.InventoryMng.utils.ExcelUtils;
import com.cjh.InventoryMng.vo.GoodsOptionVO;
import com.cjh.InventoryMng.vo.MemberGoodCurrOrderInfoVO;
import com.cjh.InventoryMng.vo.MemberOrderInfoVO;
import com.cjh.InventoryMng.vo.ResultMap;
import com.github.pagehelper.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/order")
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private SysParaService sysParaService;

	@Autowired
	private OrderService orderService;

	@RequestMapping(value = "/query", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> query(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String beginDate = (String) reqMap.get("beginDate");
		String endDate = (String) reqMap.get("endDate");
		String nowDate = DateUtils.getCurOrderDate();
		if (endDate.compareTo(nowDate) >= 0) {
			resultMap.setFailed();
			resultMap.setMessage("截止时间必须为当前订购时间以前");
			return resultMap.toMap();
		}
		Page<TOrderInfo> pageOrderInfo = orderService.queryEffectiveOrders(
				((UserInfo) SecurityUtils.getSubject().getPrincipal()).getMemberBean().getMemberId(), beginDate,
				endDate, page, limit);
		List<MemberOrderInfoVO> memberOrderInfoVOList = Lists.newArrayList();
		for (TOrderInfo tOrderInfo : pageOrderInfo.getResult()) {
			TGoodsInfo tGoodsInfo = goodsService.queryGoods(tOrderInfo.getGoodId());
			if (tGoodsInfo != null) {
				MemberOrderInfoVO memberOrderInfoVO = new MemberOrderInfoVO();
				memberOrderInfoVO.setGoodName(tGoodsInfo.getGoodName());
				memberOrderInfoVO.setSpecfications(tGoodsInfo.getSpecifications());
				memberOrderInfoVO
						.setMemberPrice(new DecimalFormat("#.##").format(((double) tOrderInfo.getMemberPrice()) / 100));
				memberOrderInfoVO.setNum(tOrderInfo.getNum());
				memberOrderInfoVO.setId(tOrderInfo.getId());
				memberOrderInfoVO.setOrderTime(tOrderInfo.getOrderTime());
				memberOrderInfoVO.setOrderDate(tOrderInfo.getOrderDate());
				memberOrderInfoVO
						.setManufacturerName(sysParaService.getManufacturerName(tGoodsInfo.getManufacturerId() + ""));
				memberOrderInfoVOList.add(memberOrderInfoVO);
			}
		}
		Page<TOrderInfo> allPageOrderInfo = orderService.queryEffectiveOrders(
				((UserInfo) SecurityUtils.getSubject().getPrincipal()).getMemberBean().getMemberId(), beginDate,
				endDate);
		Integer amount = 0;
		for (TOrderInfo tOrderInfo : allPageOrderInfo.getResult()) {
			amount += tOrderInfo.getMemberPrice() * tOrderInfo.getNum();
		}
		resultMap.setDataObject(memberOrderInfoVOList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", pageOrderInfo.getTotal());
		t.put("amount", new DecimalFormat("#.##").format(((double) amount) / 100));
		return t;
	}

	@RequestMapping(value = "/order", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> order(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer goodsId = (Integer) reqMap.get("id");
		Integer num = (Integer) reqMap.get("num");
		if (num == null) {
			resultMap.setFailed();
			resultMap.setMessage("订购数不能是空");
		} else {
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("HHmmss");
			Date now = new Date();
			String nowDate = "";
			if (sdf2.format(now).compareTo("150000") >= 0) {
				nowDate = sdf1.format(new Date(now.getTime() + 24 * 60 * 60 * 1000));
			} else {
				nowDate = sdf1.format(now);
			}
			int orderNum;
			try {
				orderNum = orderService.order(
						((UserInfo) SecurityUtils.getSubject().getPrincipal()).getMemberBean().getMemberId(), goodsId,
						num, nowDate, now);
				Map<String, Object> data = Maps.newHashMap();
				data.put("orderNum", orderNum);
				Page<TOrderInfo> orders = orderService.queryEffectiveOrders(
						((UserInfo) SecurityUtils.getSubject().getPrincipal()).getMemberBean().getMemberId(), nowDate,
						nowDate);
				int orderAmount = 0;
				if (!CollectionUtils.isEmpty(orders)) {
					for (TOrderInfo tOrderInfo : orders) {
						orderAmount += tOrderInfo.getNum() * tOrderInfo.getMemberPrice();
					}
				}
				data.put("orderAmount", new DecimalFormat("#.##").format(((double) orderAmount) / 100));
				resultMap.setData(data);
			} catch (BusinessException e) {
				resultMap.setFailed();
				resultMap.setMessage("订购失败，" + e.getMessage());
			}
		}
		return resultMap.toMap();
	}

	@RequestMapping(value = "/export")
	public void exportData(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String beginDate = request.getParameter("beginDate");
		String endDate = request.getParameter("endDate");
		Page<TOrderInfo> allPageOrderInfo = orderService.queryEffectiveOrders(
				((UserInfo) SecurityUtils.getSubject().getPrincipal()).getMemberBean().getMemberId(), beginDate,
				endDate);
		List<MemberOrderInfoVO> memberOrderInfoVOList = Lists.newArrayList();
		for (TOrderInfo tOrderInfo : allPageOrderInfo.getResult()) {
			TGoodsInfo tGoodsInfo = goodsService.queryGoods(tOrderInfo.getGoodId());
			if (tGoodsInfo != null) {
				MemberOrderInfoVO memberOrderInfoVO = new MemberOrderInfoVO();
				memberOrderInfoVO.setGoodName(tGoodsInfo.getGoodName());
				memberOrderInfoVO.setSpecfications(tGoodsInfo.getSpecifications());
				memberOrderInfoVO
						.setMemberPrice(new DecimalFormat("#.##").format(((double) tOrderInfo.getMemberPrice()) / 100));
				memberOrderInfoVO.setNum(tOrderInfo.getNum());
				memberOrderInfoVO.setId(tOrderInfo.getId());
				memberOrderInfoVO.setOrderTime(tOrderInfo.getOrderTime());
				memberOrderInfoVO.setOrderDate(tOrderInfo.getOrderDate());
				memberOrderInfoVO
						.setManufacturerName(sysParaService.getManufacturerName(tGoodsInfo.getManufacturerId() + ""));
				memberOrderInfoVOList.add(memberOrderInfoVO);
			}
		}
		String fileName = beginDate + "到" + endDate + "的订单查询结果下载-";
		fileName += DateUtils.getTheDayStr(new Date(), "yyyyMMddHHmmss") + ".xlsx";
		String userAgent = request.getHeader("User-Agent");

		// 针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} else {
			// 非IE浏览器的处理：
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		}
		try {
			byte[] bfile = ExcelUtils.createExcelContent(memberOrderInfoVOList, MemberOrderInfoVO.class);
			if (bfile != null) {
				output(fileName, bfile, response);
			} else {
				response.sendError(404, "导出内容为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("出现异常", e);
			response.setStatus(500);
			response.sendError(500);
		}
		log.info("exportUsedPhone end...");
	}

	private void output(String name, byte[] body, HttpServletResponse response) throws IOException {
		response.reset();
		response.addHeader("Content-Disposition", "attachment;filename=" + name);
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		try {
			response.setContentType("application/octet-stream");
			toClient.write(body);
			toClient.flush();
		} catch (Exception e) {
			log.error("导出文件出现异常", e);
		} finally {
			toClient.close();
		}
	}

	@RequestMapping(value = "/queryCurrOrder", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryCurrOrder(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer manufacturerId = null;
		String goodName = (String) reqMap.get("goodName");
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		if (!("0").equals((String) reqMap.get("manufacturerId")) && reqMap.get("manufacturerId") != null) {
			manufacturerId = Integer.valueOf((String) reqMap.get("manufacturerId"));
		}
		String nowDate = DateUtils.getCurOrderDate();
		Page<TGoodsInfo> goodInfoList = goodsService.queryMemberEffectiveGoodsByManufacturerGoodsName(
				((UserInfo) SecurityUtils.getSubject().getPrincipal()).getMemberBean().getMemberId(), manufacturerId,
				goodName, page, limit);
		List<MemberGoodCurrOrderInfoVO> memberGoodInfoVOList = Lists.newArrayList();
		for (TGoodsInfo tGoodsInfo : goodInfoList) {
			MemberGoodCurrOrderInfoVO memberGoodInfoVO = new MemberGoodCurrOrderInfoVO();
			BeanUtils.copyProperties(tGoodsInfo, memberGoodInfoVO);
			int num = orderService.queryEffectiveOrderGoodNum(
					((UserInfo) SecurityUtils.getSubject().getPrincipal()).getMemberBean().getMemberId(), nowDate,
					tGoodsInfo.getId());
			memberGoodInfoVO.setNum(num);
			memberGoodInfoVO
					.setManufacturerName(sysParaService.getManufacturerName(tGoodsInfo.getManufacturerId() + ""));
			memberGoodInfoVO
					.setMemberPrice(new DecimalFormat("#.##").format(((double) tGoodsInfo.getMemberPrice()) / 100));
			memberGoodInfoVOList.add(memberGoodInfoVO);
		}
		resultMap.setDataObject(memberGoodInfoVOList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", goodInfoList.getTotal());
		Page<TOrderInfo> orders = orderService.queryEffectiveOrders(
				((UserInfo) SecurityUtils.getSubject().getPrincipal()).getMemberBean().getMemberId(), nowDate, nowDate);
		int orderAmount = 0;
		if (!CollectionUtils.isEmpty(orders)) {
			for (TOrderInfo tOrderInfo : orders) {
				orderAmount += tOrderInfo.getNum() * tOrderInfo.getMemberPrice();
			}
		}
		t.put("orderAmount", new DecimalFormat("#.##").format(((double) orderAmount) / 100));
		return t;
	}
}
