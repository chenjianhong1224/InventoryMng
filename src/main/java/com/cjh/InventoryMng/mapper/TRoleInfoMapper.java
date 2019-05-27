package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TRoleInfo;
import com.cjh.InventoryMng.entity.TRoleInfoExample;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TRoleInfoMapper {
    long countByExample(TRoleInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TRoleInfo record);

    int insertSelective(TRoleInfo record);

    Page<TRoleInfo> selectByExample(TRoleInfoExample example);

    TRoleInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TRoleInfo record, @Param("example") TRoleInfoExample example);

    int updateByExample(@Param("record") TRoleInfo record, @Param("example") TRoleInfoExample example);

    int updateByPrimaryKeySelective(TRoleInfo record);

    int updateByPrimaryKey(TRoleInfo record);
}