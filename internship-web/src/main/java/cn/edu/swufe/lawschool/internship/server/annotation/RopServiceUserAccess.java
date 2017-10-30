package cn.edu.swufe.lawschool.internship.server.annotation;

import cn.edu.swufe.lawschool.internship.user.model.UserType;

import java.lang.annotation.*;

/**
 * Created on 2016年10月21
 * <p>Title:       rop服务接口的用户能访问的用户类型</p>
 * <p>Description: rop服务接口的用户能访问的用户类型</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RopServiceUserAccess {
    int[] value() default UserType.DEFAULT;
}
