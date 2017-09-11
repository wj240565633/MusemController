package com.cn.hnust.model;

public class SearchModel {
	
	  private String museumId;
      private String museumName;
      private String pictureAddress;
      private String status;
      
	public String getMuseumId() {
		return museumId;
	}
	public void setMuseumId(String museumId) {
		this.museumId = museumId;
	}
	public String getMuseumName() {
		return museumName;
	}
	public void setMuseumName(String museumName) {
		this.museumName = museumName;
	}
	public String getPictureAddress() {
		return pictureAddress;
	}
	public void setPictureAddress(String pictureAddress) {
		this.pictureAddress = pictureAddress;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "SearchModel [museumId=" + museumId + ", museumName=" + museumName + ", pictureAddress=" + pictureAddress
				+ ", status=" + status + "]";
	}
	
}
