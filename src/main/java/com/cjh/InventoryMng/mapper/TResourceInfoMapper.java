package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TResourceInfo;
import com.cjh.InventoryMng.entity.TResourceInfoExample;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TResourceInfoMapper {
    long countByExample(TResourceInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TResourceInfo record);

    int insertSelective(TResourceInfo record);

    Page<TResourceInfo> selectByExample(TResourceInfoExample example);

    TResourceInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TResourceInfo record, @Param("example") TResourceInfoExample example);

    int updateByExample(@Param("record") TResourceInfo record, @Param("example") TResourceInfoExample example);

    int updateByPrimaryKeySelective(TResourceInfo record);

    int updateByPrimaryKey(TResourceInfo record);
}