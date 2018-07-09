package com.lhkj.cgjservice.bean;

/**
 * Created by user on 2018/3/10.
 */

public class LiPinItem {
    public String id;
    public String imgv_url;
    public String name;
    public String time;
    public String type;

    public LiPinItem(String id, String imgv_url, String name,String time,String type) {
        this.id = id;
        this.imgv_url = imgv_url;
        this.name = name;
        this.time=time;
        this.type=type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgv_url() {
        return imgv_url;
    }

    public void setImgv_url(String imgv_url) {
        this.imgv_url = imgv_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
