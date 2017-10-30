package cn.edu.swufe.lawschool.internship.app.service;

import cn.edu.swufe.lawschool.internship.app.model.AppSecret;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2016年10月13
 * <p>Title:       app 密钥服务</p>
 * <p>Description: app 密钥服务</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public interface AppSecretService {

    /**
     * Created on 2016年10月13
     * <p>Description: 分页获取密钥</p>
     * @return
     */
    List<AppSecret> getAppSecret(AppSecret appSecret, Page page);

    /**
     * Created on 2016年10月13
     * <p>Description: 通过appId获取密钥</p>
     * @return
     */
    AppSecret getAppSecretByAppId(String appId);

    /**
     * Created on 2016年10月13
     * <p>Description: 插入app secret</p>
     */
    void insertAppSecret(AppSecret appSecret, String operator);

    /**
     * Created on 2016年10月13
     * <p>Description: 更新app secret</p>
     */
    void updateAppSecret(AppSecret appSecret, String operator);

    /**
     * Created on 2016年10月13
     * <p>Description: 删除app secret</p>
     */
    void deleteAppSecret(Long appSecretId);

    /**
     * Created on 2016年10月13
     * <p>Description: 生成appId</p>
     */
    String createAppId();

    /**
     * Created on 2016年10月13
     * <p>Description: 生成secret</p>
     */
    String createAppSecret(String appId);
}
