package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TFinanicalOpLog;
import com.cjh.InventoryMng.entity.TFinanicalOpLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TFinanicalOpLogMapper {
    long countByExample(TFinanicalOpLogExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TFinanicalOpLog record);

    int insertSelective(TFinanicalOpLog record);

    List<TFinanicalOpLog> selectByExample(TFinanicalOpLogExample example);

    TFinanicalOpLog selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TFinanicalOpLog record, @Param("example") TFinanicalOpLogExample example);

    int updateByExample(@Param("record") TFinanicalOpLog record, @Param("example") TFinanicalOpLogExample example);

    int updateByPrimaryKeySelective(TFinanicalOpLog record);

    int updateByPrimaryKey(TFinanicalOpLog record);
}