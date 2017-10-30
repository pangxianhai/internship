package cn.edu.swufe.lawschool.internship.android.bussiness.user.service;

import android.content.Context;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;

/**
 * Created on 2017年03月07
 * <p>Title:       登录服务</p>
 * <p>Description: 登录服务</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */

public interface LoginService {

    /**
     * 用户登陆
     * @return
     */
    UserInfo login (Context context, String userName, String password);

    /**
     * 获取当前已经登陆的用户信息
     * @return
     */
    UserInfo getLoginInfo (Context context);

    /**
     * 当前是否登陆
     * @return
     */
    boolean isLogin (Context context);
}
