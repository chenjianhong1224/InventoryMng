package com.cjh.InventoryMng.bean;

import java.util.List;

public class PlatformProfitImportBean {

	private String memberName;

	private String brandName;
	
	private List<ProfitBean> profitBeans;

	public List<ProfitBean> getProfitBeans() {
		return profitBeans;
	}

	public void setProfitBeans(List<ProfitBean> profitBeans) {
		this.profitBeans = profitBeans;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public static class ProfitBean {

		private String orderDate;

		private Integer meituanProfit;

		private Integer elemeProfit;

		public String getOrderDate() {
			return orderDate;
		}

		public void setOrderDate(String orderDate) {
			this.orderDate = orderDate;
		}

		public Integer getMeituanProfit() {
			return meituanProfit;
		}

		public void setMeituanProfit(Integer meituanProfit) {
			this.meituanProfit = meituanProfit;
		}

		public Integer getElemeProfit() {
			return elemeProfit;
		}

		public void setElemeProfit(Integer elemeProfit) {
			this.elemeProfit = elemeProfit;
		}
	}
}
