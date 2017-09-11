package com.cn.hnust.pojo;

import java.util.List;

public class HomePageBean {
	
    /**
     * msg : 运行成功!
     * code : 100
     * data : [{"museumId":"11","museumName":"大唐博物馆a12","pictureAddress":"ni456","langList":[{"languageId":"1",
     * "languageType":"汉语","price":null},{"languageId":"2","languageType":"英语","price":null}]}]
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
         * museumId : 11
         * museumName : 大唐博物馆a12
         * pictureAddress : ni456
         * langList : [{"languageId":"1","languageType":"汉语","price":null},{"languageId":"2","languageType":"英语",
         * "price":null}]
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
    }
}
