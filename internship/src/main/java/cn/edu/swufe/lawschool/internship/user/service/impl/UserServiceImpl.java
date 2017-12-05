package cn.edu.swufe.lawschool.internship.user.service.impl;

import cn.edu.swufe.lawschool.common.constants.Status;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.UserError;
import cn.edu.swufe.lawschool.internship.user.mapper.UserMapper;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import cn.edu.swufe.lawschool.internship.user.util.PasswordUtil;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import com.xavier.commons.util.CollectionUtil;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.AESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created on 2015年11月09
 * <p>Title:       用户服务类</p>
 * <p>Description: 用户服务实现</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo login(String userName, String password) {
        UserInfo userInfo = getUserInfoByUerName(userName);
        if (userInfo == null) {
            throw new InternshipException(UserError.USER_NOT_EXIST);
        }
        String _password = PasswordUtil.passwordHash(userName, password);
        if (_password.equals(userInfo.getPassword())) {
            return userInfo;
        } else {
            throw new InternshipException(UserError.PASSWORD_ERROR);
        }
    }

    @Override
    public Long addUser(UserInfo userInfo) {
        UserInfo _userInfo = getUserInfoByUerName(userInfo.getUserName());
        if (_userInfo != null) {
            throw new InternshipException(UserError.USER_NAME_DUPLICATE);
        }
        userInfo.setPassword(PasswordUtil.passwordHash(userInfo.getUserName(), userInfo.getPassword()));
        userInfo.setStatus(Status.VALID);
        userInfo.setCreatedTime(DateUtil.currentMilliseconds());
        userInfo.setUpdatedBy(userInfo.getCreatedBy());
        userInfo.setUpdatedTime(userInfo.getCreatedTime());
        int count = userMapper.insert(userInfo);
        if (count < 1) {
            throw new InternshipException(UserError.ADD_USER_ERROR);
        }
        return userInfo.getId();
    }

    @Override
    public List<UserInfo> getUserInfo(UserInfo userInfo, Page page) {
        return userMapper.select(userInfo, page);
    }

    @Override
    public UserInfo resetResetPassword(String userDestId, String operator) {
        Long userId = Long.parseLong(AESUtil.decrypt(userDestId));
        UserInfo userInfo = getUserInfoById(userId);
        if (userInfo == null) {
            throw new InternshipException(UserError.USER_NOT_EXIST);
        }
        String newPassword = createPassword();
        UserInfo _user = new UserInfo();
        _user.setId(userId);
        _user.setPassword(PasswordUtil.passwordHash(userInfo.getUserName(), newPassword));
        update(_user, operator);
        userInfo.setPassword(newPassword);
        return userInfo;
    }

    @Override
    public UserInfo changePassword(Long userId, String password, String operator) {
        UserInfo userInfo = getUserInfoById(userId);
        UserInfo _userInfo = new UserInfo();
        _userInfo.setId(userId);
        _userInfo.setPassword(PasswordUtil.passwordHash(userInfo.getUserName(), password));
        update(_userInfo, operator);
        userInfo.setPassword(_userInfo.getPassword());
        return userInfo;
    }

    @Override
    public UserInfo getUserInfoById(Long userId) {
        if (userId == null) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(userId);
        return selectOne(userInfo);
    }

    @Override
    public UserInfo getUserInfoByUerName(String userName) {
        if (StringUtil.isEmpty(userName)) {
            return null;
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(userName);
        return selectOne(userInfo);
    }

    @Override
    public UserInfo getSysAdmin() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(UserType.SYS_ADMIN);
        return selectOne(userInfo);
    }

    @Override
    public void update(UserInfo userInfo, String operator) {
        if (userInfo.getId() == null && StringUtil.isEmpty(userInfo.getUserName())) {
            throw new InternshipException(UserError.UPDATE_USER_KEY_ERROR);
        }
        userInfo.setUpdatedBy(operator);
        userInfo.setUpdatedTime(DateUtil.currentMilliseconds());
        int count = userMapper.update(userInfo);
        if (count < 1) {
            throw new InternshipException(UserError.UPDATE_USER_ERROR);
        }
    }

    @Override
    public void delete(Long userId) {
        if (userId == null) {
            throw new InternshipException(UserError.DELETE_USER_ID_EMPTY);
        }
        int count = userMapper.delete(userId);
        if (count < 1) {
            throw new InternshipException(UserError.DELETE_USER_FAILED);
        }
    }

    private UserInfo selectOne(UserInfo userInfo) {
        List<UserInfo> userInfos = userMapper.select(userInfo);
        if (CollectionUtil.isNotEmpty(userInfos)) {
            return userInfos.get(0);
        } else {
            return null;
        }
    }

    private String createPassword() {
        UUID uuid = UUID.randomUUID();
        String password = uuid.toString().replaceAll("-", "").substring(0, 6);
        return password;
    }
}
