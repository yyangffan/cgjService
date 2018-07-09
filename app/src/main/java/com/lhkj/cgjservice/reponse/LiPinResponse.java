package com.lhkj.cgjservice.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 2018/3/10.
 */

public class LiPinResponse extends HttpReponse{

    /**
     * code : 200
     * success : 参数丢失
     * order : [{"order_id":1389,"order_sn":"H15123878885","original_img":"/Public/upload/goods/2017/08-14/59917e0ed03aa.jpg","goods_id":186,"goods_name":"福特（FORD）汽车玻璃水 0°1.8L*2瓶装","add_time":1506669158,"types":1},{"order_id":1388,"order_sn":"H15120619874","original_img":"/Public/upload/goods/2017/09-22/59c4ddaa01c82.png","goods_id":195,"goods_name":"测试商品","add_time":1506669126,"types":1},{"order_id":1387,"order_sn":"H15114398699","original_img":"/Public/upload/goods/2017/09-22/59c4ddaa01c82.png","goods_id":195,"goods_name":"测试商品","add_time":1506669103,"types":1}]
     * qiandao : [{"id":43,"send_time":"1502715158","goods_id":144,"goods_name":"OPPO A37 2GB+16GB内存版 玫瑰金色 全网通4G手机 双卡双待","goods_img":"/Public/upload/goods/2017/08-14/5991719603224.jpg","types":2},{"id":44,"send_time":"1502715288","goods_id":147,"goods_name":"Apple iPhone 6 32GB 金色 移动联通电信4G手机 【818手机狂欢节】iPhone为","goods_img":"/Public/upload/goods/2017/08-14/59917201c204a.jpg","types":2},{"id":49,"send_time":"1502716592","goods_id":147,"goods_name":"Apple iPhone 6 32GB 金色 移动联通电信4G手机 【818手机狂欢节】iPhone为","goods_img":"/Public/upload/goods/2017/08-14/59917201c204a.jpg","types":2}]
     */

    @SerializedName("success")
    private String success;
    @SerializedName("order")
    private List<OrderBean> order;
    @SerializedName("qiandao")
    private List<QiandaoBean> qiandao;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public List<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(List<OrderBean> order) {
        this.order = order;
    }

    public List<QiandaoBean> getQiandao() {
        return qiandao;
    }

    public void setQiandao(List<QiandaoBean> qiandao) {
        this.qiandao = qiandao;
    }

    public static class OrderBean {
        /**
         * order_id : 1389
         * order_sn : H15123878885
         * original_img : /Public/upload/goods/2017/08-14/59917e0ed03aa.jpg
         * goods_id : 186
         * goods_name : 福特（FORD）汽车玻璃水 0°1.8L*2瓶装
         * add_time : 1506669158
         * types : 1
         */

        @SerializedName("order_id")
        private String orderId;
        @SerializedName("order_sn")
        private String orderSn;
        @SerializedName("original_img")
        private String originalImg;
        @SerializedName("goods_id")
        private String goodsId;
        @SerializedName("goods_name")
        private String goodsName;
        @SerializedName("add_time")
        private String addTime;
        @SerializedName("types")
        private String types;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public String getOriginalImg() {
            return originalImg;
        }

        public void setOriginalImg(String originalImg) {
            this.originalImg = originalImg;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }
    }

    public static class QiandaoBean {
        /**
         * id : 43
         * send_time : 1502715158
         * goods_id : 144
         * goods_name : OPPO A37 2GB+16GB内存版 玫瑰金色 全网通4G手机 双卡双待
         * goods_img : /Public/upload/goods/2017/08-14/5991719603224.jpg
         * types : 2
         */

        @SerializedName("id")
        private String id;
        @SerializedName("send_time")
        private String sendTime;
        @SerializedName("goods_id")
        private String goodsId;
        @SerializedName("goods_name")
        private String goodsName;
        @SerializedName("goods_img")
        private String goodsImg;
        @SerializedName("types")
        private String types;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSendTime() {
            return sendTime;
        }

        public void setSendTime(String sendTime) {
            this.sendTime = sendTime;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getGoodsImg() {
            return goodsImg;
        }

        public void setGoodsImg(String goodsImg) {
            this.goodsImg = goodsImg;
        }

        public String getTypes() {
            return types;
        }

        public void setTypes(String types) {
            this.types = types;
        }
    }
}
