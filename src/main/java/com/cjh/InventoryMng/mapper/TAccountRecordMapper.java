package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TAccountRecord;
import com.cjh.InventoryMng.entity.TAccountRecordExample;
import com.cjh.InventoryMng.entity.TAccountRecordWithBLOBs;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TAccountRecordMapper {
    long countByExample(TAccountRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TAccountRecordWithBLOBs record);

    int insertSelective(TAccountRecordWithBLOBs record);

    List<TAccountRecordWithBLOBs> selectByExampleWithBLOBs(TAccountRecordExample example);

    Page<TAccountRecord> selectByExample(TAccountRecordExample example);

    TAccountRecordWithBLOBs selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TAccountRecordWithBLOBs record, @Param("example") TAccountRecordExample example);

    int updateByExampleWithBLOBs(@Param("record") TAccountRecordWithBLOBs record, @Param("example") TAccountRecordExample example);

    int updateByExample(@Param("record") TAccountRecord record, @Param("example") TAccountRecordExample example);

    int updateByPrimaryKeySelective(TAccountRecordWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TAccountRecordWithBLOBs record);

    int updateByPrimaryKey(TAccountRecord record);
}