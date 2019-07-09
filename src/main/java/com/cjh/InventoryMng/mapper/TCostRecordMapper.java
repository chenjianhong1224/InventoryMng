package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TCostRecord;
import com.cjh.InventoryMng.entity.TCostRecordExample;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCostRecordMapper {
    long countByExample(TCostRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TCostRecord record);

    int insertSelective(TCostRecord record);

    Page<TCostRecord> selectByExample(TCostRecordExample example);

    TCostRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TCostRecord record, @Param("example") TCostRecordExample example);

    int updateByExample(@Param("record") TCostRecord record, @Param("example") TCostRecordExample example);

    int updateByPrimaryKeySelective(TCostRecord record);

    int updateByPrimaryKey(TCostRecord record);
}