package com.cjh.InventoryMng.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cjh.InventoryMng.entity.TIntroducer;
import com.cjh.InventoryMng.entity.TIntroducerExample;
import com.cjh.InventoryMng.mapper.TIntroducerMapper;
import com.cjh.InventoryMng.service.IntroducerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
@Transactional
public class IntroducerServiceImpl implements IntroducerService {

	@Autowired
	private TIntroducerMapper mapper;

	@Override
	public long countByExample(TIntroducerExample example) {
		return mapper.countByExample(example);
	}

	@Override
	public int deleteByPrimaryKey(Integer id) {
		return mapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(TIntroducer record) {
		return mapper.insert(record);
	}

	@Override
	public int insertSelective(TIntroducer record) {
		return mapper.insertSelective(record);
	}

	@Override
	public Page<TIntroducer> selectByExample(TIntroducerExample example, int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return mapper.selectByExample(example);
	}

	@Override
	public TIntroducer selectByPrimaryKey(Integer id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByExampleSelective(TIntroducer record, TIntroducerExample example) {
		return mapper.updateByExampleSelective(record, example);
	}

	@Override
	public int updateByExample(TIntroducer record, TIntroducerExample example) {
		return mapper.updateByExample(record, example);
	}

	@Override
	public int updateByPrimaryKeySelective(TIntroducer record) {
		return mapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(TIntroducer record) {
		return mapper.updateByPrimaryKey(record);
	}

}
