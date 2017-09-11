package com.cn.hnust.kotlinPro.bean;

import java.util.List;

public class DataBean<T> {
	private String msg;
	private String code;
	private List<T> data;
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
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "DataBean [msg=" + msg + ", code=" + code + ", t=" + data + "]";
	}
	
	

}
