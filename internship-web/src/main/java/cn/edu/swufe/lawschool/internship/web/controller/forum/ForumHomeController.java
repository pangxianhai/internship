package cn.edu.swufe.lawschool.internship.web.controller.forum;

import cn.edu.swufe.lawschool.internship.bulletin.service.BulletinService;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 2016年06月24
 * <p>Title:       论坛</p>
 * <p>Description: 论坛</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  O2O业务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/forum")
public class ForumHomeController {

    @Autowired
    private BulletinService bulletinService;

    @RequestMapping(value = "/home.htm", method = RequestMethod.GET)
    @LoginAccessPermission
    public String bulletinList(ModelMap modelMap) {
        return "forum/forumHome";
    }

}
