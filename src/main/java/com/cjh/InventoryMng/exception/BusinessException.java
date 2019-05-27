package com.cjh.InventoryMng.exception;

public class BusinessException extends Exception {
	
	public String getExcpCode() {
		return excpCode;
	}

	public void setExcpCode(String excpCode) {
		this.excpCode = excpCode;
	}

	private String excpCode;

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String excpCode, String message) {
		super(message);
		this.excpCode = excpCode;
	}

}
