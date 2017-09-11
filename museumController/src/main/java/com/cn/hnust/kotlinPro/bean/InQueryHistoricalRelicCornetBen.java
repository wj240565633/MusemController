package com.cn.hnust.kotlinPro.bean;

import java.util.List;


/**
 * 查询博物馆文物短号列表实体
 * @author liuxiongfei
 *
 */
public class InQueryHistoricalRelicCornetBen {
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
        private List<RelicListBean> relicList;

        public List<RelicListBean> getRelicList() {
            return relicList;
        }

        public void setRelicList(List<RelicListBean> relicList) {
            this.relicList = relicList;
        }

        public static class RelicListBean {
            /**
             * museumName : 大唐博物馆
             * museumId: 11
             * pictureAddress:http://www.baidu.com
             * cornetList : [{"historicalRelicName":"刘雄飞6","cornette":"4354"},{"historicalRelicName":"刘雄飞4","cornette":"6349"},{"historicalRelicName":"刘雄飞2","cornette":"2547"},{"historicalRelicName":"刘雄飞7","cornette":"4545"},{"historicalRelicName":"刘雄飞5","cornette":"4335"},{"historicalRelicName":"刘雄飞3","cornette":"1234"},{"historicalRelicName":"刘雄飞1","cornette":"1475"}]
             */

            private String museumName;
            private String museumId;
            private String pictureAddress;
            private List<CornetListBean> cornetList;
            
            public String getMuseumId() {
				return museumId;
			}
            public void setMuseumId(String museumId) {
				this.museumId = museumId;
			}
            public String getPictureAddress() {
				return pictureAddress;
			}
            
            public void setPictureAddress(String pictureAddress) {
				this.pictureAddress = pictureAddress;
			}

            public String getMuseumName() {
                return museumName;
            }

            public void setMuseumName(String museumName) {
                this.museumName = museumName;
            }

            public List<CornetListBean> getCornetList() {
                return cornetList;
            }

            public void setCornetList(List<CornetListBean> cornetList) {
                this.cornetList = cornetList;
            }

            public static class CornetListBean {
                /**
                 * historicalRelicName : 刘雄飞6
                 * cornette : 4354
                 */

                private String historicalRelicName;
                private String cornette;

                public String getHistoricalRelicName() {
                    return historicalRelicName;
                }

                public void setHistoricalRelicName(String historicalRelicName) {
                    this.historicalRelicName = historicalRelicName;
                }

                public String getCornette() {
                    return cornette;
                }

                public void setCornette(String cornette) {
                    this.cornette = cornette;
                }
            }
        }
    }

}
