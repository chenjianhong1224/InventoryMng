package com.cjh.InventoryMng.bean;

import java.io.Serializable;
import java.util.List;

import com.cjh.InventoryMng.entity.TResourceInfo;
import com.cjh.InventoryMng.entity.TUserInfo;



public class UserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private TUserInfo tUserInfo;
	
	private MemberBean userBean;

	public MemberBean getMemberBean() {
		return userBean;
	}

	public void setMemberBean(MemberBean userBean) {
		this.userBean = userBean;
	}

	public TUserInfo gettUserInfo() {
		return tUserInfo;
	}

	public void settUserInfo(TUserInfo tUserInfo) {
		this.tUserInfo = tUserInfo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private List<TResourceInfo> tResourceInfoList;

	@Override
	public String toString() {
		return tUserInfo == null ? null : tUserInfo.getUserId();
	}

	public List<TResourceInfo> gettResourceInfoList() {
		return tResourceInfoList;
	}

	public void settResourceInfoList(List<TResourceInfo> tResourceInfoList) {
		this.tResourceInfoList = tResourceInfoList;
	}
}
