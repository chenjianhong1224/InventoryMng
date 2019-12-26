package com.cjh.InventoryMng.service;

import org.apache.ibatis.annotations.Param;

import com.cjh.InventoryMng.entity.TIntroducer;
import com.cjh.InventoryMng.entity.TIntroducerExample;
import com.github.pagehelper.Page;

public interface IntroducerService {
	long countByExample(TIntroducerExample example);

	int deleteByPrimaryKey(Integer id);

	int insert(TIntroducer record);

	int insertSelective(TIntroducer record);

	Page<TIntroducer> selectByExample(TIntroducerExample example, int pageNo, int pageSize);

	TIntroducer selectByPrimaryKey(Integer id);

	int updateByExampleSelective(@Param("record") TIntroducer record, @Param("example") TIntroducerExample example);

	int updateByExample(@Param("record") TIntroducer record, @Param("example") TIntroducerExample example);

	int updateByPrimaryKeySelective(TIntroducer record);

	int updateByPrimaryKey(TIntroducer record);
}
