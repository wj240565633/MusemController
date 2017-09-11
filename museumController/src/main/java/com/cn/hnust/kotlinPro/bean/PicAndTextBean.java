package com.cn.hnust.kotlinPro.bean;

public class PicAndTextBean {
	
	//标题
	private String title;
	//描述
	private String desc;
	//图片地址
	private String picUrl;
	//跳转地址
	private String jumpUrl;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	public String getJumpUrl() {
		return jumpUrl;
	}
	public void setJumpUrl(String jumpUrl) {
		this.jumpUrl = jumpUrl;
	}
	@Override
	public String toString() {
		return "PicAndTextBean [title=" + title + ", desc=" + desc + ", picUrl=" + picUrl + ", jumpUrl=" + jumpUrl
				+ "]";
	}
	
	

}
