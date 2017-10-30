package cn.edu.swufe.lawschool.internship.android.bussiness.attend.service;

import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.AttendRecord;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.AttendRecordParam;
import cn.edu.swufe.lawschool.internship.android.bussiness.attend.model.TimeInterval;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.Page;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.page.PageResult;
import cn.edu.swufe.lawschool.internship.android.bussiness.user.model.UserInfo;

import java.util.List;

/**
 * Created on 2017年05月03
 * <p>Title:       签到业务</p>
 * <p>Description: 签到业务</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public interface AttendService {

    /**
     * 学生签到
     * @param loginInfo 当前用户信息
     * @return
     */
    Boolean studentAttend (UserInfo loginInfo, String attendDay, TimeInterval timeInterval);

    /**
     * 获取签到结果
     * @param recordParam 查询条件
     * @param loginInfo   当前用户信息
     * @return
     */
    PageResult<List<AttendRecord>> getAttendRecord (UserInfo loginInfo, AttendRecordParam recordParam);

    /**
     * 删除签到记录
     * @param attendDesId 签到记录加密Id
     * @param loginInfo   当前用户信息
     * @return
     */
    Boolean deleteAttend (UserInfo loginInfo, String attendDesId);
}
