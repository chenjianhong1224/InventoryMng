package com.cjh.InventoryMng.service;

public interface EmailService {
	
	public boolean sendAllOrderEmail(String orderDate);
	
	public boolean sendMemberOrderEmail(String orderDate, Integer memberId);

}
