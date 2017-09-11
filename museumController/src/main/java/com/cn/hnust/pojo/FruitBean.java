package com.cn.hnust.pojo;

import java.util.List;


public class FruitBean {
	
	private String msg;
    private String code;
    private List<DataBean> data;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<DataBean> getData() {
		return data;
	}
	public void setData(List<DataBean> data) {
		this.data = data;
	}
    
	 public static class DataBean {
		 
		 private String total;
		 private String number;
		 private String discountId;
		 private String saletotal;
		 private String languageType;
		 
		 
		 
		 
		 public String getLanguageType() {
			return languageType;
		}
		public void setLanguageType(String languageType) {
			this.languageType = languageType;
		}
		public String getDiscountId() {
			return discountId;
		}
		 public String getSaletotal() {
			return saletotal;
		}
		 
		 public void setDiscountId(String discountId) {
			this.discountId = discountId;
		}
		 
		 public void setSaletotal(String saletotal) {
			this.saletotal = saletotal;
		}
		 
		 
		public String getTotal() {
			return total;
		}
		public void setTotal(String total) {
			this.total = total;
		}
		public String getNumber() {
			return number;
		}
		public void setNumber(String number) {
			this.number = number;
		}
		 
		 
	 }

}
