package com.cjh.InventoryMng.mapper;

import com.cjh.InventoryMng.entity.TCompanyProfit;
import com.cjh.InventoryMng.entity.TCompanyProfitExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TCompanyProfitMapper {
    long countByExample(TCompanyProfitExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TCompanyProfit record);

    int insertSelective(TCompanyProfit record);

    List<TCompanyProfit> selectByExample(TCompanyProfitExample example);

    TCompanyProfit selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TCompanyProfit record, @Param("example") TCompanyProfitExample example);

    int updateByExample(@Param("record") TCompanyProfit record, @Param("example") TCompanyProfitExample example);

    int updateByPrimaryKeySelective(TCompanyProfit record);

    int updateByPrimaryKey(TCompanyProfit record);
}