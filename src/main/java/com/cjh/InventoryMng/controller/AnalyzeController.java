package com.cjh.InventoryMng.controller;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cjh.InventoryMng.vo.ManjianVO;
import com.cjh.InventoryMng.vo.ResultMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@Controller
@RequestMapping(value = "/analyze")
public class AnalyzeController {

	@RequestMapping(value = "/analyzeManjianPage")
	public String analyzeManjianPage(Model model) {
		return "analyze/analyze_manjian";
	}

	@RequestMapping(value = "/analyzeManjian", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> analyzeManjian(@RequestBody Map<String, Object> reqMap) {
		ResultMap resultMap = ResultMap.one();
		Integer page = (Integer) reqMap.get("page");
		Integer limit = (Integer) reqMap.get("limit");
		String distributionFee = (String) reqMap.get("distributionFee");
		String atLeast = (String) reqMap.get("atLeast");
		String percent = (String) reqMap.get("percent");
		String isAgent = (String) reqMap.get("isAgent");
		String costRatio = (String) reqMap.get("costRatio");
		String profitRatio = (String) reqMap.get("profitRatio");
		List<ManjianVO> returnList = Lists.newArrayList();
		int size = 60;
		if (StringUtils.isEmpty(profitRatio)) {
			profitRatio = "1";
		}
		if (StringUtils.isEmpty(distributionFee)) {
			returnList = null;
			size = 0;
		} else {
			int beginGear = 15;
			int length = 12;
			for (int gear = beginGear + (page - 1) * limit; gear < beginGear + page * limit; gear++) {
				ManjianVO vo = new ManjianVO();
				if (isAgent.equals("0")) {
					// Z=(atLeast>(gear-distributionFee-X)*costRatio)?atLeast:(gear-distributionFee-X)*costRatio
					// (gear-distributionFee-Z-X)/gear>percent
					int i = 1;
					int tmpGear = gear;
					do {
						double tmp = Double.valueOf(atLeast) / Double.valueOf(costRatio);
						double x = 0.0;
						x = tmpGear - Double.valueOf(distributionFee)
								- (tmpGear * Double.valueOf(costRatio) * Double.valueOf(profitRatio))
										/ (1 - Double.valueOf(percent));
						if (tmpGear - Double.valueOf(distributionFee) - x < tmp) {
							x = tmpGear - Double.valueOf(distributionFee) - Double.valueOf(atLeast)
									- tmpGear * Double.valueOf(costRatio) * Double.valueOf(profitRatio);
						}
						if (i == 1) {
							vo.setGear1(tmpGear + "");
							vo.setReduce1(new DecimalFormat("#.##").format(x));
							tmpGear += length;
						} else {
							vo.setGear2(tmpGear + "");
							vo.setReduce2(new DecimalFormat("#.##").format(x));
						}
					} while (i++ < 2);
				} else {
					int i = 1;
					int tmpGear = gear;
					do {
						double x = 0.0;
						x = tmpGear * (1 - Double.valueOf(percent)) - Double.valueOf(distributionFee)
								- (tmpGear * Double.valueOf(costRatio) * Double.valueOf(profitRatio));
						if (i == 1) {
							vo.setGear1(tmpGear + "");
							vo.setReduce1(new DecimalFormat("#.##").format(x));
							tmpGear += length;
						} else {
							vo.setGear2(tmpGear + "");
							vo.setReduce2(new DecimalFormat("#.##").format(x));
						}
					} while (i++ < 2);
				}
				returnList.add(vo);
			}
		}
		resultMap.setDataList(returnList);
		Map<String, Object> t = resultMap.toMap();
		t.put("count", size);
		return t;
	}
}
