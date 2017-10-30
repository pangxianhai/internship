package cn.edu.swufe.lawschool.internship.web.controller.webApp;

import cn.edu.swufe.lawschool.internship.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.flow.model.FlowType;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created on 2017年02月03
 * <p>Title:       待审核消息</p>
 * <p>Description: 待审核消息</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@Controller("webExamineMessageController")
@RequestMapping(value = "/webApp/examine")
public class ExamineMessageController {
    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({ UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE })
    public String getNeedExamineMessage (ModelMap modelMap, FlowRecord flowRecord) {
        modelMap.put("flowRecord", flowRecord);
        modelMap.put("flowTypes", FlowType.getValues());
        return "webApp/examine/examineMessage";
    }
}
