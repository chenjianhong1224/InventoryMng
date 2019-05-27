package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TUserInfo;
import com.cjh.InventoryMng.entity.TUserInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TUserInfoMapper {
    long countByExample(TUserInfoExample example);

    int deleteByPrimaryKey(String userId);

    int insert(TUserInfo record);

    int insertSelective(TUserInfo record);

    List<TUserInfo> selectByExample(TUserInfoExample example);

    TUserInfo selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") TUserInfo record, @Param("example") TUserInfoExample example);

    int updateByExample(@Param("record") TUserInfo record, @Param("example") TUserInfoExample example);

    int updateByPrimaryKeySelective(TUserInfo record);

    int updateByPrimaryKey(TUserInfo record);
}