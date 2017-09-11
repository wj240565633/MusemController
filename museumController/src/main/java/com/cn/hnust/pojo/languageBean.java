package com.cn.hnust.pojo;

import java.util.List;

/**
 * Created on 2017/3/17.
 * 作者：by thanatos
 * 作用：
 */

public class languageBean {


    /**
     * msg :
     * code :
     * data : [{"languageId":"","languageType":"","price":""}]
     */

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
        /**
         * languageId :
         * languageType :
         * price :
         */

        private String languageId;
        private String languageType;
        private String price;

        public String getLanguageId() {
            return languageId;
        }

        public void setLanguageId(String languageId) {
            this.languageId = languageId;
        }

        public String getLanguageType() {
            return languageType;
        }

        public void setLanguageType(String languageType) {
            this.languageType = languageType;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

		@Override
		public String toString() {
			return "DataBean [languageId=" + languageId + ", languageType=" + languageType + ", price=" + price + "]";
		}
        
        
    }
}
