package com.cn.hnust.kotlinPro.bean;

import java.util.List;

public class PurchaseRecordBean {
	 /**
     * msg : 杩愯鎴愬姛!
     * code : 100
     * data : [{"buyTime":"2017-04-06 18:15:49","historicalRelicId":"2","historicalRelicName":"鍒橀泟椋�2","level":"涓�绾ф枃鐗�","dynasty":"鍏堢Е","museumName":"鍖椾含鍗氱墿棣�","pictureAddress":"https://www.baidu.com/img/bd_logo1.png","listen":"YES","museumId":"13","languageId":"1"},{"buyTime":"2017-04-06 18:12:21","historicalRelicId":"1","historicalRelicName":"鍒橀泟椋�1","level":"涓�绾ф枃鐗�","dynasty":"鏋楄豹","museumName":"澶у攼鍗氱墿棣�","pictureAddress":"https://www.baidu.com/img/bd_logo1.png","listen":"NO","museumId":"11","languageId":"1"}]
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
         * buyTime : 2017-04-06 18:15:49
         * historicalRelicId : 2
         * historicalRelicName : 鍒橀泟椋�2
         * level : 涓�绾ф枃鐗�
         * dynasty : 鍏堢Е
         * museumName : 鍖椾含鍗氱墿棣�
         * pictureAddress : https://www.baidu.com/img/bd_logo1.png
         * listen : YES
         * museumId : 13
         * languageId : 1
         */

        private String buyTime;
        private String historicalRelicId;
        private String historicalRelicName;
        private String level;
        private String dynasty;
        private String museumName;
        private String pictureAddress;
        private String listen;
        private String museumId;
        private String languageId;
        private String languageType;
        
        

        public String getLanguageType() {
			return languageType;
		}

		public void setLanguageType(String languageType) {
			this.languageType = languageType;
		}

		public String getBuyTime() {
            return buyTime;
        }

        public void setBuyTime(String buyTime) {
            this.buyTime = buyTime;
        }

        public String getHistoricalRelicId() {
            return historicalRelicId;
        }

        public void setHistoricalRelicId(String historicalRelicId) {
            this.historicalRelicId = historicalRelicId;
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

        public String getListen() {
            return listen;
        }

        public void setListen(String listen) {
            this.listen = listen;
        }

        public String getMuseumId() {
            return museumId;
        }

        public void setMuseumId(String museumId) {
            this.museumId = museumId;
        }

        public String getLanguageId() {
            return languageId;
        }

        public void setLanguageId(String languageId) {
            this.languageId = languageId;
        }
    }
}
