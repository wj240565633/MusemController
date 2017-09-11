package com.cn.hnust.pojo;

import java.util.List;


public class MuseumBean {
	
	private String msg;
    private String code;
    private List<SearchBean> data;
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
	public List<SearchBean> getData() {
		return data;
	}
	public void setData(List<SearchBean> data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "MuseumBean [msg=" + msg + ", code=" + code + ", data=" + data + "]";
	}
	

	public static class SearchBean { 
        /**
         * museumId :
         * museumName :
         * pictureAddress :
         */

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
			return "SearchBean [museumId=" + museumId + ", museumName=" + museumName + ", pictureAddress="
					+ pictureAddress + "]";
		}
        
 }
}
