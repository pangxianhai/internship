package cn.edu.swufe.lawschool.internship.server.service;

import cn.edu.swufe.lawschool.internship.user.model.UserInfo;

/**
 * Created on 2016年10月21
 * <p>Title:       rop登录服务</p>
 * <p>Description: rop登录服务</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public interface RopLoginService {

    /**
     * Created on 2016年10月21
     * <p>Description: 保存登录用户信息</p>
     * @author 庞先海
     */
    void saveLoginUserInfo(UserInfo userInfo);

    /**
     * Created on 2016年10月21
     * <p>Description: 获取登录用户信息</p>
     * @author 庞先海
     */
    UserInfo getLoginUserInfo();
}
