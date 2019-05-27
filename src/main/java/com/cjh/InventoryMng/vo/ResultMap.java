package com.cjh.InventoryMng.vo;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import com.google.common.collect.Maps;

public class ResultMap {

	private int code = 0;
	Map<String, Object> data = Maps.newHashMap();
	Object dataObject;
	List dataList = Lists.newArrayList();

	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}

	public void setDataList(List data) {
		dataList = data;
	}

	String message = "success";

	/**
	 * success
	 * 
	 * @return
	 */
	public static ResultMap one() {
		ResultMap m = new ResultMap();
		return m;
	}

	public ResultMap setSuccess() {
		setCode(0);
		return this;
	}

	public ResultMap setFailed() {
		setCode(-1);
		return this;
	}

	public ResultMap setMessage(String message) {
		this.message = message;
		return this;
	}

	public ResultMap setData(Map<String, ? extends Object> data) {
		this.data.putAll(data);
		return this;
	}

	public ResultMap addData(String key, Object value) {
		this.data.put(key, value);
		return this;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> newMap = Maps.newHashMap();
		if (dataObject != null) {
			newMap.put("data", dataObject);
		} else if (data != null && data.size() != 0) {
			newMap.put("data", data);
		} else if (dataList != null) {
			newMap.put("data", dataList);
		}
		newMap.put("code", getCode());
		newMap.put("message", message);
		return newMap;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}
}
