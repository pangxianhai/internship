package cn.edu.swufe.lawschool.internship.web.annotation;

import cn.edu.swufe.lawschool.internship.user.model.UserType;

import java.lang.annotation.*;

/**
 * Created on 2015年11月11
 * <p>Title:       登录权限</p>
 * <p>Description: 登录权限</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginAccessPermission {
    int[] value() default UserType.DEFAULT;
}



