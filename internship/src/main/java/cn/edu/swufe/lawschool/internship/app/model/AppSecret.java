package cn.edu.swufe.lawschool.internship.app.model;

import cn.edu.swufe.lawschool.common.base.BaseDO;
import cn.edu.swufe.lawschool.common.constants.Status;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.xavier.commons.util.StringUtil;

/**
 * Created on 2016年10月13
 * <p>Title:       app secret实体</p>
 * <p>Description: app secret实体</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AppSecret extends BaseDO {

    /**
     * appId
     */
    private String appId;

    /**
     * app secret
     */
    private String appSecret;

    /**
     * 描述
     */
    private String description;

    /**
     * 公钥
     */
    private String publicKey;

    /**
     * 私钥
     */
    private String privateKey;

    public String getAppId () {
        return appId;
    }

    public void setAppId (String appId) {
        this.appId = StringUtil.trimToNull(appId);
    }

    public String getAppSecret () {
        return appSecret;
    }

    public void setAppSecret (String appSecret) {
        this.appSecret = StringUtil.trimToNull(appSecret);
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = StringUtil.trimToNull(description);
    }

    public String getPublicKey () {
        return publicKey;
    }

    public void setPublicKey (String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey () {
        return privateKey;
    }

    public void setPrivateKey (String privateKey) {
        this.privateKey = privateKey;
    }
}
