package com.cn.hnust.pojo;

import java.util.List;

/**
 * Created on 2017/3/17.
 * 浣滆�咃細by thanatos
 * 浣滅敤锛�
 */

public class ListeningBean {


	 /**
     * msg : 鏂囩墿宸茶喘涔帮紝鐩存帴鏀跺惉锛�
     * code : 206
     * data : [{"soundAddress":"yuyingdizhi","describe":"鍗氱墿棣嗘弿杩�","historicalRelicName":"姘ㄥ熀閰稿挴浣燼","freeNumber":null,"pictureAddressList":[{"pictureAddress":"https://www.baidu.com/img/bd_logo1.png"},{"pictureAddress":"https://www.baidu.com/img/bd_logo1.png"}]}]
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
         * soundAddress : yuyingdizhi
         * describe : 鍗氱墿棣嗘弿杩�
         * historicalRelicName : 姘ㄥ熀閰稿挴浣燼
         * freeNumber : null
         * pictureAddressList : [{"pictureAddress":"https://www.baidu.com/img/bd_logo1.png"},{"pictureAddress":"https://www.baidu.com/img/bd_logo1.png"}]
         */

        private String soundAddress;
        private String describe1;
        private String historicalRelicName;
        private Object freeNumber;
        private List<PictureAddressListBean> pictureAddressList;
        private String buy;
        
        

		public String getBuy() {
			return buy;
		}

		public void setBuy(String buy) {
			this.buy = buy;
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

        public String getHistoricalRelicName() {
            return historicalRelicName;
        }

        public void setHistoricalRelicName(String historicalRelicName) {
            this.historicalRelicName = historicalRelicName;
        }

        public Object getFreeNumber() {
            return freeNumber;
        }

        public void setFreeNumber(Object freeNumber) {
            this.freeNumber = freeNumber;
        }

        public List<PictureAddressListBean> getPictureAddressList() {
            return pictureAddressList;
        }

        public void setPictureAddressList(List<PictureAddressListBean> pictureAddressList) {
            this.pictureAddressList = pictureAddressList;
        }

        public static class PictureAddressListBean {
            /**
             * pictureAddress : https://www.baidu.com/img/bd_logo1.png
             */

            private String pictureAddress;

            public String getPictureAddress() {
                return pictureAddress;
            }

            public void setPictureAddress(String pictureAddress) {
                this.pictureAddress = pictureAddress;
            }
        }
    }
}
