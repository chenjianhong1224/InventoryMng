package com.cjh.InventoryMng;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.cjh.InventoryMng.mapper")
public class InventoryMngApplication {
	public static void main(String[] args) {
		SpringApplication.run(InventoryMngApplication.class, args);
	}
}
