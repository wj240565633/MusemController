package com.cn.hnust.pojo;

import java.util.List;


/**
 * Created on 2017/3/16.
 * 作者：by thanatos
 * 作用：
 */

public class IntroduceBean {
	
	
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




		/**
         * museumName :
         * pictureAddress :
         * soundAddress :
         * describe :
         */
    public static class DataBean {
        private String museumName;
        private String pictureAddress;
        private String soundAddress;
        private String describe1;

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

        public String getSoundAddress() {
            return soundAddress;
        }

        public void setSoundAddress(String soundAddress) {
            this.soundAddress = soundAddress;
        }

        public String getDescribe() {
            return describe1;
        }

        public void setDescribe(String describe1) {
            this.describe1 = describe1;
        }

		@Override
		public String toString() {
			return "IntroduceBean [museumName=" + museumName + ", pictureAddress=" + pictureAddress + ", soundAddress="
					+ soundAddress + ", describe=" + describe1 + "]";
		}
    }

}
