package cn.edu.swufe.lawschool.internship.server;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.app.model.AppSecret;
import cn.edu.swufe.lawschool.internship.app.service.AppSecretService;
import com.xavier.rop.security.AppSecretManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created on 2016年10月13
 * <p>Title:       app secret 管理器</p>
 * <p>Description: app secret 管理器</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class InternshipAppSecretManager implements AppSecretManager {

    @Autowired
    AppSecretService appSecretService;

    public String getAppSecret(String appId) {
        return appSecretService.getAppSecretByAppId(appId).getAppSecret();
    }

    public boolean isValidAppId(String appId) {
        AppSecret appSecret = appSecretService.getAppSecretByAppId(appId);
        return appSecret.getStatus().equals(Status.VALID);
    }
}
