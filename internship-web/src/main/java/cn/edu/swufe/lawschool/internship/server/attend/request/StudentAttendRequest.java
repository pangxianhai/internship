package cn.edu.swufe.lawschool.internship.server.attend.request;

import com.xavier.rop.request.AbstractRopRequest;
import com.xavier.rop.validation.annotation.NotNull;

/**
 * Created on 2016年10月21
 * <p>Title:       学生签到参数</p>
 * <p>Description: 学生签到参数</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class StudentAttendRequest extends AbstractRopRequest {

    /**
     * 签到日期
     */
    String attendDay;

    /**
     * 签到时段
     */
    @NotNull
    Integer timeIntervalCode;

    public String getAttendDay () {
        return attendDay;
    }

    public void setAttendDay (String attendDay) {
        this.attendDay = attendDay;
    }

    public Integer getTimeIntervalCode () {
        return timeIntervalCode;
    }

    public void setTimeIntervalCode (Integer timeIntervalCode) {
        this.timeIntervalCode = timeIntervalCode;
    }
}
