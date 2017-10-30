package cn.edu.swufe.lawschool.internship.web.controller;

import cn.edu.swufe.lawschool.common.util.PageUtil;
import cn.edu.swufe.lawschool.internship.flow.model.FlowRecord;
import cn.edu.swufe.lawschool.internship.flow.model.FlowStatus;
import cn.edu.swufe.lawschool.internship.flow.model.FlowType;
import cn.edu.swufe.lawschool.internship.flow.service.FlowService;
import cn.edu.swufe.lawschool.internship.user.service.LoginService;
import cn.edu.swufe.lawschool.internship.user.model.UserInfo;
import cn.edu.swufe.lawschool.internship.user.model.UserType;
import cn.edu.swufe.lawschool.internship.web.annotation.LoginAccessPermission;
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
 * Created on 2015年11月26
 * <p>Title:       待审核消息</p>
 * <p>Description: 待审核消息</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/examine")
public class ExamineMessageController {

    @Autowired
    FlowService flowService;

    @Autowired
    LoginService loginService;


    @RequestMapping(value = "/list.htm", method = RequestMethod.GET)
    @LoginAccessPermission({UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE})
    public String getNeedExamineMessage(ModelMap modelMap, FlowRecord flowRecord) {
        modelMap.put("flowRecord", flowRecord);
        modelMap.put("flowTypes", FlowType.getValues());
        return "examine/examineMessage";
    }

    @RequestMapping(value = "/list.json", method = RequestMethod.POST)
    @ResponseBody
    @LoginAccessPermission({UserType.TEACHER_CODE, UserType.COMPANY_TUTOR_CODE})
    public Object getNeedExamineMessage(FlowRecord flowRecord, @RequestParam(required = false, defaultValue = "1") Integer currentPage) {
        UserInfo loginInfo = loginService.getLoginUserInfo();
        Page page = new Page(PageUtil.filter(currentPage), 10);
        flowRecord.setFlowStatus(FlowStatus.NEED_EXAMINE);
        flowRecord.setOperateUserId(loginInfo.getId());
        List<FlowRecord> flowRecords = flowService.getFlowRecord(flowRecord, page);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("flowRecords", flowRecords);
        data.put("page", page);
        return data;
    }
}
