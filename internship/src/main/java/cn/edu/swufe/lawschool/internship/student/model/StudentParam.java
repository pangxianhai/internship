package cn.edu.swufe.lawschool.internship.student.model;

/**
 * Created on 2016年07月14
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class StudentParam extends Student {
    /**
     * 开始时间
     */
    Long registerBeginTime;

    /**
     * 结束时间
     */
    Long registerEndTime;

    /**
     * 移除导师
     */
    Boolean removeTutor;

    /**
     * 是否更新学院信息
     */
    Boolean updateUniversity;

    public Long getRegisterBeginTime () {
        return registerBeginTime;
    }

    public void setRegisterBeginTime (Long registerBeginTime) {
        this.registerBeginTime = registerBeginTime;
    }

    public Long getRegisterEndTime () {
        return registerEndTime;
    }

    public void setRegisterEndTime (Long registerEndTime) {
        this.registerEndTime = registerEndTime;
    }

    public Boolean getRemoveTutor () {
        return removeTutor;
    }

    public void setRemoveTutor (Boolean removeTutor) {
        this.removeTutor = removeTutor;
    }

    public Boolean getUpdateUniversity () {
        if (updateUniversity == null) {
            return false;
        }
        return updateUniversity;
    }

    public void setUpdateUniversity (Boolean updateUniversity) {
        this.updateUniversity = updateUniversity;
    }
}
