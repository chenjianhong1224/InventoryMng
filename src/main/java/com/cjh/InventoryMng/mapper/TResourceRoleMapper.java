package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TResourceRole;
import com.cjh.InventoryMng.entity.TResourceRoleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TResourceRoleMapper {
    long countByExample(TResourceRoleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TResourceRole record);

    int insertSelective(TResourceRole record);

    List<TResourceRole> selectByExample(TResourceRoleExample example);

    TResourceRole selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TResourceRole record, @Param("example") TResourceRoleExample example);

    int updateByExample(@Param("record") TResourceRole record, @Param("example") TResourceRoleExample example);

    int updateByPrimaryKeySelective(TResourceRole record);

    int updateByPrimaryKey(TResourceRole record);
}