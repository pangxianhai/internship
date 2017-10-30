package cn.edu.swufe.lawschool.internship.web.model;

import java.io.Serializable;

/**
 * Created on 2015年11月15
 * <p>Title:       上传图片返回结果</p>
 * <p>Description: 上传图片返回结果</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class ImageResponse implements Serializable {
    private String url;
    private String title;
    private String state;

    public ImageResponse(){}

    public ImageResponse(String state){
        this.state = state;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
