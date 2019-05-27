package com.cjh.InventoryMng.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cjh.InventoryMng.service.GoodsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodsTest {

	@Autowired
	private GoodsService goodsService;

	@Test
	public void addGoods() {
		goodsService.addGoods("玉米蒸饺", 1, 1, "10包/件 18克/个 约28个/包", 9800, 12800, null, null, "cjh");
	}

}
