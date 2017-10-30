package cn.edu.swufe.lawschool.common.logger;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created on 2015年11月05
 * <p>Title:       日志注解</p>
 * <p>Description: 日志注解</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */


@Retention(RUNTIME)
@Target(FIELD)
@Documented
@Inherited
public @interface Logger {
}
