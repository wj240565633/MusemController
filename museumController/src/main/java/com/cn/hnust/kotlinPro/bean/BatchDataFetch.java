package com.cn.hnust.kotlinPro.bean;

import java.util.List;

public class BatchDataFetch {

    /**
     * msg : 运行成功!
     * code : 100
     * data : [{"historicalRelicId":"2","museumId":"11","historicalRelicName":"你叫了你简历","level":"一级文物","dynasty":"先秦","pictureAddress":"https://www.baidu.com/img/bd_logo1.png","buy":null},{"historicalRelicId":"3","museumId":"11","historicalRelicName":"林豪文物","level":"一级文物","dynasty":"侏罗纪","pictureAddress":"https://www.baidu.com/img/bd_logo1.png","buy":null},{"historicalRelicId":"1","museumId":"11","historicalRelicName":"氨基酸咯你a","level":"一级文物","dynasty":"林豪","pictureAddress":"https://www.baidu.com/img/bd_logo1.png","buy":null},{"historicalRelicId":"1","museumId":"11","historicalRelicName":"氨基酸咯你a","level":"一级文物","dynasty":"林豪","pictureAddress":"https://www.baidu.com/img/bd_logo1.png","buy":null},{"historicalRelicId":"3","museumId":"11","historicalRelicName":"林豪文物","level":"一级文物","dynasty":"侏罗纪","pictureAddress":"https://www.baidu.com/img/bd_logo1.png","buy":null},{"historicalRelicId":"4","museumId":"11","historicalRelicName":"你叫了你简历","level":"一级文物","dynasty":"先秦","pictureAddress":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1850903515,3370641070&fm=21&gp=0.jpg","buy":null},{"historicalRelicId":"5","museumId":"11","historicalRelicName":"林豪文物","level":"一级文物","dynasty":"林豪","pictureAddress":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1850903515,3370641070&fm=21&gp=0.jpg","buy":null},{"historicalRelicId":"6","museumId":"11","historicalRelicName":"你叫了你简历","level":"一级文物","dynasty":"先秦","pictureAddress":"https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1850903515,3370641070&fm=21&gp=0.jpg","buy":null}]
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
         * historicalRelicId : 2
         * museumId : 11
         * historicalRelicName : 你叫了你简历
         * level : 一级文物
         * dynasty : 先秦
         * pictureAddress : https://www.baidu.com/img/bd_logo1.png
         * buy : null
         */

        private String historicalRelicId;
        private String museumId;
        private String historicalRelicName;
        private String level;
        private String dynasty;
        private String pictureAddress;
        private Object buy;

        public String getHistoricalRelicId() {
            return historicalRelicId;
        }

        public void setHistoricalRelicId(String historicalRelicId) {
            this.historicalRelicId = historicalRelicId;
        }

        public String getMuseumId() {
            return museumId;
        }

        public void setMuseumId(String museumId) {
            this.museumId = museumId;
        }

        public String getHistoricalRelicName() {
            return historicalRelicName;
        }

        public void setHistoricalRelicName(String historicalRelicName) {
            this.historicalRelicName = historicalRelicName;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getDynasty() {
            return dynasty;
        }

        public void setDynasty(String dynasty) {
            this.dynasty = dynasty;
        }

        public String getPictureAddress() {
            return pictureAddress;
        }

        public void setPictureAddress(String pictureAddress) {
            this.pictureAddress = pictureAddress;
        }

        public Object getBuy() {
            return buy;
        }

        public void setBuy(Object buy) {
            this.buy = buy;
        }
    }
}
