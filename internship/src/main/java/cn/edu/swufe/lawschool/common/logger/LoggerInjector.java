package cn.edu.swufe.lawschool.common.logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * Created on 2015年11月05
 * <p>Title:       日志拦截器</p>
 * <p>Description: 日志拦截器</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class LoggerInjector implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(final Object bean, String beanName) throws BeansException {
        ReflectionUtils.doWithFields(bean.getClass(), new ReflectionUtils.FieldCallback() {
            public void doWith(Field field) throws IllegalArgumentException,
                    IllegalAccessException {
                // make the field accessible if defined private
                ReflectionUtils.makeAccessible(field);
                if (field.getAnnotation(Logger.class) != null) {
                    Log log = LogFactory.getLog(bean.getClass());
                    field.set(bean, log);
                }
            }
        });
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
