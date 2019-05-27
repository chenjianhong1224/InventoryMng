package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TMemberInfo;
import com.cjh.InventoryMng.entity.TMemberInfoExample;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TMemberInfoMapper {
    long countByExample(TMemberInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TMemberInfo record);

    int insertSelective(TMemberInfo record);

    Page<TMemberInfo> selectByExample(TMemberInfoExample example);

    TMemberInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TMemberInfo record, @Param("example") TMemberInfoExample example);

    int updateByExample(@Param("record") TMemberInfo record, @Param("example") TMemberInfoExample example);

    int updateByPrimaryKeySelective(TMemberInfo record);

    int updateByPrimaryKey(TMemberInfo record);
}