package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TGroupMap;
import com.cjh.InventoryMng.entity.TGroupMapExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TGroupMapMapper {
    long countByExample(TGroupMapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TGroupMap record);

    int insertSelective(TGroupMap record);

    List<TGroupMap> selectByExample(TGroupMapExample example);

    TGroupMap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TGroupMap record, @Param("example") TGroupMapExample example);

    int updateByExample(@Param("record") TGroupMap record, @Param("example") TGroupMapExample example);

    int updateByPrimaryKeySelective(TGroupMap record);

    int updateByPrimaryKey(TGroupMap record);
}