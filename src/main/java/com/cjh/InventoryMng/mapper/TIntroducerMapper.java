package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TIntroducer;
import com.cjh.InventoryMng.entity.TIntroducerExample;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TIntroducerMapper {
    long countByExample(TIntroducerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TIntroducer record);

    int insertSelective(TIntroducer record);

    Page<TIntroducer> selectByExample(TIntroducerExample example);

    TIntroducer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TIntroducer record, @Param("example") TIntroducerExample example);

    int updateByExample(@Param("record") TIntroducer record, @Param("example") TIntroducerExample example);

    int updateByPrimaryKeySelective(TIntroducer record);

    int updateByPrimaryKey(TIntroducer record);
}