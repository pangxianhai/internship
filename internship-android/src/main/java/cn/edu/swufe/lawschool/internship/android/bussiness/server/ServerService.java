package cn.edu.swufe.lawschool.internship.android.bussiness.server;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.PageResult;
import cn.edu.swufe.lawschool.internship.android.bussiness.server.model.AccessParam;

import java.util.List;

/**
 * Created on 2017年03月08
 * <p>Title:       服务访问接口</p>
 * <p>Description: 服务访问接口</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public interface ServerService {

    /**
     * 访问后台服务
     * @return
     */
    <T> T access (AccessParam accessParam, Class<T> typeClass);

    /**
     * 访问后台服务
     * @return list
     */
    <T> List<T> accessList (AccessParam accessParam, Class<T> typeClass);

    /**
     * 访问后台服务
     * @param accessParam
     * @param dataKey
     * @return
     */
    <T> PageResult<List<T>> access (AccessParam accessParam, String dataKey, Class<T> typeClass);
}
