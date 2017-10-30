package cn.edu.swufe.lawschool.internship.web.controller.user;

import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.exception.UserError;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.user.service.UserService;
import cn.edu.swufe.lawschool.internship.user.util.PasswordUtil;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2015年11月12
 * <p>Title:       用户controller</p>
 * <p>Description: 用户操作</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public String getUserList(ModelMap modelMap, UserInfo userInfo) {
        modelMap.put("userInfo", userInfo);
        modelMap.put("userTypeList", UserType.getValues());
        return "user/userList";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public Object getUserListData(UserInfo userInfo,
                                  @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        Map<String, Object> data = new HashMap<String, Object>();
        Page page = new Page(PageUtil.filter(currentPage), 10, Order.formString("id.DESC"));
        List<UserInfo> userInfoList = userService.getUserInfo(userInfo, page);
        data.put("userInfoList", userInfoList);
        data.put("page", page);
        return data;
    }

    @RequestMapping(value = "/reset_password.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission(UserType.SYS_ADMIN_CODE)
    public Object resetPassword(String userDesId) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        return userService.resetResetPassword(userDesId, loginInfo.getName());
    }

    @RequestMapping(value = "/change_password.htm", method = RequestMethod.GET)
    @LoginAccessPermission({UserType.SYS_ADMIN_CODE, UserType.COMPANY_TUTOR_CODE, UserType.TEACHER_CODE, UserType.STUDENT_CODE})
    public Object changePassword() {
        return "user/changePassword";
    }

    @RequestMapping(value = "/change_password.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission({UserType.SYS_ADMIN_CODE, UserType.COMPANY_TUTOR_CODE, UserType.TEACHER_CODE, UserType.STUDENT_CODE})
    public boolean changePasswordAction(String sourcePassword, String password, String passwordAgain) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        UserInfo currentUserInfo = userService.getUserInfoById(loginInfo.getId());
        if (!currentUserInfo.getPassword().equals(
                PasswordUtil.passwordHash(currentUserInfo.getUserName(), sourcePassword))) {
            throw new InternshipException(UserError.SOURCE_PASSWORD_ERROR);
        }
        if (!password.equals(passwordAgain)) {
            throw new InternshipException(UserError.PASSWORD_AGAIN_ERROR);
        }
        userService.changePassword(loginInfo.getId(), password, loginInfo.getName());
        return true;
    }

    @RequestMapping(value = "/checkUserName.json", method = RequestMethod.GET)
    @ResponseBody
    public boolean checkUserName(String userName) {
        return userService.getUserInfoByUerName(userName) == null;
    }

    @RequestMapping(value = "/forget_password.htm", method = RequestMethod.GET)
    public String forgetPassword(ModelMap modelMap) {
        UserInfo admin = userService.getSysAdmin();
        modelMap.put("admin", admin);
        return "user/forgetPassword";
    }

}
