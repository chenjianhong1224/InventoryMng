package com.cjh.InventoryMng.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjh.InventoryMng.bean.EmailBean;
import com.cjh.InventoryMng.constants.Constants;
import com.cjh.InventoryMng.entity.TSysParam;
import com.cjh.InventoryMng.entity.TSysParamExample;
import com.cjh.InventoryMng.mapper.TSysParamMapper;
import com.cjh.InventoryMng.service.SysParaService;
import com.google.common.collect.Lists;

@Service
@Transactional
public class SysParaServiceImpl implements SysParaService {

	@Autowired
	private TSysParamMapper tSysParamMapper;

	@Override
	public List<TSysParam> getAllBrand() {
		TSysParamExample example = new TSysParamExample();
		example.createCriteria().andParamNameEqualTo(Constants.BrandsysParamName);
		List<TSysParam> resultList = tSysParamMapper.selectByExample(example);
		return resultList;
	}

	@Override
	public String getBrandName(String brandId) {
		TSysParamExample example = new TSysParamExample();
		example.createCriteria().andParamNameEqualTo(Constants.BrandsysParamName).andParamKeyEqualTo(brandId);
		List<TSysParam> resultList = tSysParamMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(resultList)) {
			return null;
		}
		return resultList.get(0).getParamValue();
	}

	@Override
	public String getManufacturerName(String manufacturerId) {
		TSysParamExample example = new TSysParamExample();
		example.createCriteria().andParamNameEqualTo(Constants.ManufacturersysParamName)
				.andParamKeyEqualTo(manufacturerId);
		List<TSysParam> resultList = tSysParamMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(resultList)) {
			return null;
		}
		return resultList.get(0).getParamValue();
	}

	@Override
	public List<TSysParam> getAllManufacturer() {
		TSysParamExample example = new TSysParamExample();
		example.createCriteria().andParamNameEqualTo(Constants.ManufacturersysParamName);
		List<TSysParam> resultList = tSysParamMapper.selectByExample(example);
		return resultList;
	}

	@Override
	public String getBrandId(String brandName) {
		TSysParamExample example = new TSysParamExample();
		example.createCriteria().andParamNameEqualTo(Constants.BrandsysParamName).andParamValueEqualTo(brandName);
		List<TSysParam> resultList = tSysParamMapper.selectByExample(example);
		return CollectionUtils.isEmpty(resultList) ? null : resultList.get(0).getParamKey();
	}

	@Override
	public List<String> getOrderEmailAddress() {
		TSysParamExample example = new TSysParamExample();
		example.createCriteria().andParamNameEqualTo(Constants.OrderReceiveEmailParamName);
		List<TSysParam> resultList = tSysParamMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(resultList)) {
			return null;
		}
		List<String> returlList = Lists.newArrayList();
		for (TSysParam t : resultList) {
			returlList.add(t.getParamValue());
		}
		return returlList;
	}

	@Override
	public EmailBean getSysEmail() {
		TSysParamExample example = new TSysParamExample();
		example.createCriteria().andParamNameEqualTo(Constants.SysEmailParamName);
		List<TSysParam> resultList = tSysParamMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(resultList)) {
			return null;
		}
		String v = resultList.get(0).getParamValue();
		String elems[] = v.split("\\|");
		EmailBean bean = new EmailBean();
		bean.setHost(elems[0]);
		bean.setPort(Integer.valueOf(elems[1]));
		bean.setUserName(elems[2]);
		bean.setPassword(elems[3]);
		return bean;
	}

}
