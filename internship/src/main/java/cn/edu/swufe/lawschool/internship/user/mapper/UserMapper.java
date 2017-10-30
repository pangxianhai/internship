package cn.edu.swufe.lawschool.internship.user.mapper;

import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月08
 * <p>Title:       用户mapper</p>
 * <p>Description: 用户信息的查询，增加，修改</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface UserMapper {

    /**
     * Created on 2015年11月08
     * <p>Description:根据条件查询用户信息</p>
     * @author 庞先海
     */
    List<UserInfo> select(UserInfo userInfo);

    /**
     * Created on 2015年11月08
     * <p>Description:根据条件分页查询用户信息</p>
     * @author 庞先海
     */
    List<UserInfo> select(UserInfo userInfo, Page page);

    /**
     * Created on 2015年11月08
     * <p>Description:插入用户信息</p>
     * @author 庞先海
     */
    int insert(UserInfo userInfo);

    /**
     * Created on 2015年11月08
     * <p>Description:更新用户信息</p>
     * @author 庞先海
     */
    int update(UserInfo userInfo);

    /**
     * Created on 2016年07月11
     * <p>Description:删除用户</p>
     * @author 庞先海
     */
    int delete(Long id);
}
