package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TSupplier;
import com.cjh.InventoryMng.entity.TSupplierExample;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSupplierMapper {
    long countByExample(TSupplierExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSupplier record);

    int insertSelective(TSupplier record);

    Page<TSupplier> selectByExample(TSupplierExample example);

    TSupplier selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSupplier record, @Param("example") TSupplierExample example);

    int updateByExample(@Param("record") TSupplier record, @Param("example") TSupplierExample example);

    int updateByPrimaryKeySelective(TSupplier record);

    int updateByPrimaryKey(TSupplier record);
}