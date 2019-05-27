package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TOrderInfo;
import com.cjh.InventoryMng.entity.TOrderInfoExample;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TOrderInfoMapper {
    long countByExample(TOrderInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TOrderInfo record);

    int insertSelective(TOrderInfo record);

    Page<TOrderInfo> selectByExample(TOrderInfoExample example);

    TOrderInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TOrderInfo record, @Param("example") TOrderInfoExample example);

    int updateByExample(@Param("record") TOrderInfo record, @Param("example") TOrderInfoExample example);

    int updateByPrimaryKeySelective(TOrderInfo record);

    int updateByPrimaryKey(TOrderInfo record);
}