package com.cn.hnust.pojo;

import java.util.List;

/**
 * Created on 2017/3/17.
 * 作者：by thanatos
 * 作用：
 */

public class HistoricalBean {

    /**
     * msg :
     * code :
     * data : [{"historicalRelicId":"","museumId":"","historicalRelicName":"","level":"","dynasty":"",
     * "pictureAddress":""}]
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
         * historicalRelicId :
         * museumId :
         * historicalRelicName :
         * level :
         * dynasty :
         * pictureAddress :
         */

        private String historicalRelicId;
        private String museumId;
        private String historicalRelicName;
        private String level;
        private String dynasty;
        private String pictureAddress;
        private String buy;

        public String getBuy() {
			return buy;
		}

		public void setBuy(String buy) {
			this.buy = buy;
		}

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
    }
}
