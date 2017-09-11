package com.cn.hnust.kotlinPro.bean;

import java.util.List;

public class UserFeedBackBean {
	
	 /**
     * msg : 运行成功!
     * code : 100
     * data : [{"content":"发的发的","feedbackTime":"2017-04-11 16:14:33","answerContent":null,"answerTime":null},{"content":"按时大大","feedbackTime":"2017-04-11 16:14:33","answerContent":"爱迪生大","answerTime":"2017-04-11 16:14:33"}]
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
         * content : 发的发的
         * feedbackTime : 2017-04-11 16:14:33
         * answerContent : null
         * answerTime : null
         */

        private String content;
        private String feedbackTime;
        private Object answerContent;
        private Object answerTime;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getFeedbackTime() {
            return feedbackTime;
        }

        public void setFeedbackTime(String feedbackTime) {
            this.feedbackTime = feedbackTime;
        }

        public Object getAnswerContent() {
            return answerContent;
        }

        public void setAnswerContent(Object answerContent) {
            this.answerContent = answerContent;
        }

        public Object getAnswerTime() {
            return answerTime;
        }

        public void setAnswerTime(Object answerTime) {
            this.answerTime = answerTime;
        }
    }

}
