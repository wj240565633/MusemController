package com.cn.hnust.kotlinPro.bean;

import java.util.List;

public class UserInComeTypeBean {
	
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
         * subtotal : 1.36
         * nameList : [{"nickname":"老大","income":"0.55"},{"nickname":"听雨","income":"0.81"},{"nickname":"来听雨","income":"0.00"}]
         */

        private String subtotal;
        private List<NameListBean> nameList;

        public String getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(String subtotal) {
            this.subtotal = subtotal;
        }

        public List<NameListBean> getNameList() {
            return nameList;
        }

        public void setNameList(List<NameListBean> nameList) {
            this.nameList = nameList;
        }

        public static class NameListBean {
            /**
             * nickname : 老大
             * income : 0.55
             */

            private String nickname;
            private String income;

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getIncome() {
                return income;
            }

            public void setIncome(String income) {
                this.income = income;
            }
        }
    }

}
