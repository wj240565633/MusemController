package com.cn.hnust.model;

public class MuseumListModel {
	
    private String museumId;
    private String museumName;
    private String pictureAddress;
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
	@Override
	public String toString() {
		return "MuseumListModel [museumId=" + museumId + ", museumName=" + museumName + ", pictureAddress="
				+ pictureAddress + "]";
	}
    
    

}
