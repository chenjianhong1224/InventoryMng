package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TPlatformProfit;
import com.cjh.InventoryMng.entity.TPlatformProfitExample;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TPlatformProfitMapper {
    long countByExample(TPlatformProfitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TPlatformProfit record);

    int insertSelective(TPlatformProfit record);

    Page<TPlatformProfit> selectByExample(TPlatformProfitExample example);

    TPlatformProfit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TPlatformProfit record, @Param("example") TPlatformProfitExample example);

    int updateByExample(@Param("record") TPlatformProfit record, @Param("example") TPlatformProfitExample example);

    int updateByPrimaryKeySelective(TPlatformProfit record);

    int updateByPrimaryKey(TPlatformProfit record);
}