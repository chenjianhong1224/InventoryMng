package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TMemberReduce;
import com.cjh.InventoryMng.entity.TMemberReduceExample;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TMemberReduceMapper {
    long countByExample(TMemberReduceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMemberReduce record);

    int insertSelective(TMemberReduce record);

    Page<TMemberReduce> selectByExample(TMemberReduceExample example);

    TMemberReduce selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMemberReduce record, @Param("example") TMemberReduceExample example);

    int updateByExample(@Param("record") TMemberReduce record, @Param("example") TMemberReduceExample example);

    int updateByPrimaryKeySelective(TMemberReduce record);

    int updateByPrimaryKey(TMemberReduce record);
}