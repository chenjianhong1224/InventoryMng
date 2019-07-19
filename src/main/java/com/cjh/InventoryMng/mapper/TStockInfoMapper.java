package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TStockInfo;
import com.cjh.InventoryMng.entity.TStockInfoExample;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TStockInfoMapper {
    long countByExample(TStockInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TStockInfo record);

    int insertSelective(TStockInfo record);

    Page<TStockInfo> selectByExample(TStockInfoExample example);

    TStockInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TStockInfo record, @Param("example") TStockInfoExample example);

    int updateByExample(@Param("record") TStockInfo record, @Param("example") TStockInfoExample example);

    int updateByPrimaryKeySelective(TStockInfo record);

    int updateByPrimaryKey(TStockInfo record);
}