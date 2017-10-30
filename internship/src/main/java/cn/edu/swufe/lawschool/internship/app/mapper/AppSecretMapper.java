package cn.edu.swufe.lawschool.internship.app.mapper;

import cn.edu.swufe.lawschool.internship.app.model.AppSecret;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2016年10月13
 * <p>Title:       密钥管理mapper</p>
 * <p>Description: 密钥管理mapper</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public interface AppSecretMapper {

    /**
     * Created on 2016年10月13
     * <p>Description:根据条件查询密钥</p>
     * @author 庞先海
     */
    List<AppSecret> select(AppSecret appSecret);

    /**
     * Created on 2016年10月13
     * <p>Description:根据条件分页查询密钥</p>
     * @author 庞先海
     */
    List<AppSecret> select(AppSecret appSecret, Page page);

    /**
     * Created on 2016年10月13
     * <p>Description:插入密钥</p>
     * @author 庞先海
     */
    int insert(AppSecret appSecret);

    /**
     * Created on 2016年10月13
     * <p>Description:更新密钥</p>
     * @author 庞先海
     */
    int update(AppSecret appSecret);

    /**
     * Created on 2016年10月13
     * <p>Description:删除密钥</p>
     * @author 庞先海
     */
    int delete(Long appSecretId);
}
