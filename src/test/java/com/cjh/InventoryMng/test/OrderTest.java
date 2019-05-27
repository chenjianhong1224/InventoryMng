package com.cjh.InventoryMng.test;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cjh.InventoryMng.exception.BusinessException;
import com.cjh.InventoryMng.service.OrderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderTest {

	@Autowired
	private OrderService orderService;
	
	@Test
	public void order() throws BusinessException{
		orderService.order(1, 1, 1,"1",new Date());
	}
	
}
