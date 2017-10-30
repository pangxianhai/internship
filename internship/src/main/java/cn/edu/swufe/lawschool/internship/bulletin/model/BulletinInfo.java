package cn.edu.swufe.lawschool.internship.bulletin.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;

/**
 * Created on 2016年06月23
 * <p>Title:       公告内容</p>
 * <p>Description: 公告内容</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class BulletinInfo extends BaseDO {

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 发布公告的用户Id
     */
    private Long userId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
