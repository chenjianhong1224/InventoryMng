package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TGoodsInfo;
import com.cjh.InventoryMng.entity.TGoodsInfoExample;
import com.github.pagehelper.Page;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TGoodsInfoMapper {
    long countByExample(TGoodsInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TGoodsInfo record);

    int insertSelective(TGoodsInfo record);

    Page<TGoodsInfo> selectByExample(TGoodsInfoExample example);

    TGoodsInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TGoodsInfo record, @Param("example") TGoodsInfoExample example);

    int updateByExample(@Param("record") TGoodsInfo record, @Param("example") TGoodsInfoExample example);

    int updateByPrimaryKeySelective(TGoodsInfo record);

    int updateByPrimaryKey(TGoodsInfo record);
}