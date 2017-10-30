package cn.edu.swufe.lawschool.internship.attend.model;

/**
 * Created on 2015年11月26
 * <p>Title:       attend查询条件类</p>
 * <p>Description: attend查询条件</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class AttendRecordParam extends AttendRecord {
    /**
     * 开始时间
     */
    Long attendBeginDay;

    /**
     * 结束时间
     */
    Long attendEndDay;

    public Long getAttendBeginDay () {
        return attendBeginDay;
    }

    public void setAttendBeginDay (Long attendBeginDay) {
        this.attendBeginDay = attendBeginDay;
    }

    public Long getAttendEndDay () {
        return attendEndDay;
    }

    public void setAttendEndDay (Long attendEndDay) {
        this.attendEndDay = attendEndDay;
    }
}
