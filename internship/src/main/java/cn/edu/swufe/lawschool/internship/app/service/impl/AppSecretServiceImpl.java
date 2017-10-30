package cn.edu.swufe.lawschool.internship.app.service.impl;

import cn.edu.swufe.lawschool.internship.app.mapper.AppSecretMapper;
import cn.edu.swufe.lawschool.internship.app.model.AppSecret;
import cn.edu.swufe.lawschool.internship.app.service.AppSecretService;
import cn.edu.swufe.lawschool.internship.exception.AppSecretError;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.RandomUtil;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.HashUtil;
import com.xavier.commons.util.encrypt.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2016年10月13
 * <p>Title:       app 密钥服务 实现</p>
 * <p>Description: app 密钥服务 实现</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Service("appSecretService")
public class AppSecretServiceImpl implements AppSecretService {

    @Autowired
    AppSecretMapper appSecretMapper;

    private Map<String, AppSecret> appSecretCashMap = new ConcurrentHashMap<String, AppSecret>();

    public List<AppSecret> getAppSecret (AppSecret appSecret, Page page) {
        return appSecretMapper.select(appSecret, page);
    }

    public AppSecret getAppSecretByAppId (String appId) {
        if (StringUtil.isEmpty(appId)) {
            return null;
        }
        if (!appSecretCashMap.containsKey(appId)) {
            AppSecret appSecretParam = new AppSecret();
            appSecretParam.setAppId(appId);
            List<AppSecret> appSecretList = appSecretMapper.select(appSecretParam);
            if (CollectionUtil.isNotEmpty(appSecretList)) {
                appSecretCashMap.put(appId, appSecretList.get(0));
            }
        }
        return appSecretCashMap.get(appId);
    }

    public void insertAppSecret (AppSecret appSecret, String operator) {
        KeyPair keyPair = RSAUtil.generateRSAKeyPair(2048);
        appSecret.setPublicKey(RSAUtil.buildRSAKey(keyPair.getPublic()));
        appSecret.setPrivateKey(RSAUtil.buildRSAKey(keyPair.getPrivate()));
        appSecret.setCreatedBy(operator);
        appSecret.setCreatedDate(new Date());
        appSecret.setUpdatedBy(operator);
        appSecret.setUpdatedDate(new Date());
        int count = appSecretMapper.insert(appSecret);
        if (count <= 0) {
            throw new InternshipException(AppSecretError.ADD_SECRET_FAILED);
        }
    }

    public void updateAppSecret (AppSecret appSecret, String operator) {
        if (appSecret.getId() == null) {
            throw new InternshipException(AppSecretError.UPDATE_SECRET_ID_EMPTY);
        }
        if (StringUtil.isEmpty(appSecret.getAppId())) {
            throw new InternshipException(AppSecretError.UPDATE_SECRET_ID_EMPTY);
        }
        appSecret.setUpdatedBy(operator);
        appSecret.setUpdatedDate(new Date());
        int count = appSecretMapper.update(appSecret);
        if (count <= 0) {
            throw new InternshipException(AppSecretError.UPDATE_SECRET_FAILED);
        }
        if (appSecretCashMap.containsKey(appSecret.getAppId())) {
            appSecretCashMap.remove(appSecret.getAppId());
        }
    }

    public void deleteAppSecret (Long appSecretId) {
        appSecretMapper.delete(appSecretId);
    }

    public String createAppId () {
        String appId = RandomUtil.getRandCode(16, true);
        while (true) {
            if (getAppSecretByAppId(appId) == null) {
                return appId;
            }
            appId = RandomUtil.getRandCode(16, true);
        }
    }

    public String createAppSecret (String appId) {
        String _tmp = RandomUtil.getRandCode(16, false) + appId;
        return HashUtil.md5Hash(_tmp);
    }
}
