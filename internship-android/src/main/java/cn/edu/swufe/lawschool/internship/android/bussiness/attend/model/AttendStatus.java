package cn.edu.swufe.lawschool.internship.android.bussiness.attend.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseType;

import java.util.List;

/**
 * Created on 2017年05月03
 * <p>Title:       签到状态</p>
 * <p>Description: 签到状态</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AttendStatus extends BaseType {
    public final static AttendStatus ATTENDED = new AttendStatus(100, "出勤");

    public final static AttendStatus LATE = new AttendStatus(101, "迟到");

    public final static AttendStatus EARLY = new AttendStatus(102, "早退");

    public final static AttendStatus ABSENT = new AttendStatus(103, "缺席");

    public final static AttendStatus LEAVE = new AttendStatus(104, "请假");

    public final static AttendStatus APPLY = new AttendStatus(105, "审核中");

    protected AttendStatus (Integer code, String desc) {
        super(code, desc);
    }

    public static List<AttendStatus> getValues () {
        return getValues(AttendStatus.class);
    }

    public static AttendStatus parse (Integer code) {
        return parse(getValues(), code);
    }
}
