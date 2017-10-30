package cn.edu.swufe.lawschool.internship.user.service;

import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月08
 * <p>Title:       用户服务</p>
 * <p>Description: 用户服务</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public interface UserService {

    /**
     * Created on 2015年11月08
     * <p>Description: 用户登录</p>
     * @return 用户信息
     * @author 庞先海
     */
    UserInfo login(String userName, String password);

    /**
     * Created on 2015年11月08
     * <p>Description: 添加用户信息</p>
     * @return 用户Id
     * @author 庞先海
     */
    Long addUser(UserInfo userInfo);

    /**
     * Created on 2015年11月12
     * <p>Description: 分页获取用户列表</p>
     * @return 用户列表
     * @author 庞先海
     */
    List<UserInfo> getUserInfo(UserInfo userInfo, Page page);

    /**
     * Created on 2015年11月13
     * <p>Description: 通过用户加密id重置用户密码</p>
     * @param userDestId 加密id
     * @return 成功返回新密码
     * @author 庞先海
     */
    UserInfo resetResetPassword(String userDestId, String operator);

    /**
     * Created on 2015年11月16
     * <p>Description: 修改密码</p>
     * @return
     * @author 庞先海
     */
    UserInfo changePassword(Long userId, String password, String operator);

    /**
     * Created on 2015年11月16
     * <p>Description: 通过id获取用户信息</p>
     * @return
     * @author 庞先海
     */
    UserInfo getUserInfoById(Long userId);

    /**
     * Created on 2015年11月17
     * <p>Description: 通过userName获取用户信息</p>
     * @return
     * @author 庞先海
     */
    UserInfo getUserInfoByUerName(String userName);

    /**
     * Created on 2015年12月07
     * <p>Description:  获取系统管理员账号</p>
     * @return
     * @author 庞先海
     */
    UserInfo getSysAdmin();

    /**
     * Created on 2015年12月12
     * <p>Description:  更新用户信息</p>
     * @return
     * @author 庞先海
     */
    void update(UserInfo userInfo, String operator);

    /**
     * Created on 2016年07月11
     * <p>Description:  删除用户</p>
     * @return
     * @author 庞先海
     */
    void delete(Long userId);
}