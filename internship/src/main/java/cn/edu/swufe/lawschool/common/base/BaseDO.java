package cn.edu.swufe.lawschool.common.base;

import cn.edu.swufe.lawschool.common.constants.Status;
import com.xavier.commons.util.DateUtil;
import com.xavier.commons.util.NumberUtil;
import com.xavier.commons.util.StringUtil;
import com.xavier.commons.util.encrypt.AESUtil;

import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2015年11月03
 * <p>Title:       基础实体类</p>
 * <p>Description: 基础实体类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class BaseDO implements Serializable {

    /**
     * 主建Id
     */
    private Long id;

    /**
     * 主建加密Id
     */
    private String desId;

    /**
     * 状态
     */
    private Status status;

    private String createdBy;

    private Long createdTime;

    private Date createdDate;

    private String updatedBy;

    private Long updatedTime;

    private Date updatedDate;

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
        if (id != null) {
            desId = AESUtil.encrypt(String.valueOf(id));
        }
    }

    public String getCreatedBy () {
        return createdBy;
    }

    public void setCreatedBy (String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getCreatedTime () {
        if (createdTime == null) {
            createdTime = DateUtil.currentMilliseconds();
        }
        return createdTime;
    }

    public void setCreatedTime (Long createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy () {
        if (updatedBy == null) {
            updatedBy = createdBy;
        }
        return updatedBy;
    }

    public void setUpdatedBy (String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getUpdatedTime () {
        if (updatedTime == null) {
            updatedTime = DateUtil.currentMilliseconds();
        }
        return updatedTime;
    }

    public void setUpdatedTime (Long updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getDesId () {
        if (StringUtil.isEmpty(desId) && id != null) {
            desId = AESUtil.encrypt(String.valueOf(id));
        }
        return desId;
    }

    public void setDesId (String desId) {
        if (StringUtil.isNotEmpty(desId)) {
            this.id = NumberUtil.parseLong(AESUtil.decrypt(desId));
            if (this.id != null) {
                this.desId = desId;
            }
        }
    }

    public Status getStatus () {
        return this.status;
    }

    public void setStatus (Status status) {
        this.status = status;
    }

    public void setStatusCode (Integer statusCode) {
        if (statusCode != null) {
            this.status = Status.parse(statusCode);
        }
    }

    public Date getCreatedDate () {
        if (createdDate == null && createdTime != null) {
            createdDate = DateUtil.millisecondsToDate(createdTime);
        }
        return createdDate;
    }

    public void setCreatedDate (Date createdDate) {
        this.createdDate = createdDate;
        this.createdTime = DateUtil.dateToMilliseconds(createdDate);
    }

    public Date getUpdatedDate () {
        if (updatedDate == null) {
            updatedDate = DateUtil.millisecondsToDate(updatedTime);
        }
        return updatedDate;
    }

    public void setUpdatedDate (Date updatedDate) {
        this.updatedDate = updatedDate;
        this.updatedTime = DateUtil.dateToMilliseconds(updatedDate);
    }

    public Boolean getIsValid () {
        return Status.VALID.equals(status);
    }

    public Boolean getIsInvalid () {
        return Status.INVALID.equals(status);
    }

    public Boolean getIsCancel () {
        return Status.CANCEL.equals(status);
    }

    public Boolean getIsFreeze () {
        return Status.FREEZE.equals(status);
    }
}
