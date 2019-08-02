package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TGoodsBlacklist;
import com.cjh.InventoryMng.entity.TGoodsBlacklistExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TGoodsBlacklistMapper {
    long countByExample(TGoodsBlacklistExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TGoodsBlacklist record);

    int insertSelective(TGoodsBlacklist record);

    List<TGoodsBlacklist> selectByExample(TGoodsBlacklistExample example);

    TGoodsBlacklist selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TGoodsBlacklist record, @Param("example") TGoodsBlacklistExample example);

    int updateByExample(@Param("record") TGoodsBlacklist record, @Param("example") TGoodsBlacklistExample example);

    int updateByPrimaryKeySelective(TGoodsBlacklist record);

    int updateByPrimaryKey(TGoodsBlacklist record);
}