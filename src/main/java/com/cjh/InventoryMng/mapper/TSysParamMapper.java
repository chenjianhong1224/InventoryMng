package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TSysParam;
import com.cjh.InventoryMng.entity.TSysParamExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TSysParamMapper {
    long countByExample(TSysParamExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TSysParam record);

    int insertSelective(TSysParam record);

    List<TSysParam> selectByExample(TSysParamExample example);

    TSysParam selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TSysParam record, @Param("example") TSysParamExample example);

    int updateByExample(@Param("record") TSysParam record, @Param("example") TSysParamExample example);

    int updateByPrimaryKeySelective(TSysParam record);

    int updateByPrimaryKey(TSysParam record);
}