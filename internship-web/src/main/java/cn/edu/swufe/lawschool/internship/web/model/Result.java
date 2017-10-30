package cn.edu.swufe.lawschool.internship.web.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Created on 2015年09月25
 * <p>Title:       接口返回结果格式</p>
 * <p>Description: 接口返回结果格式</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "result")
public class Result implements Serializable {

    @XmlElement
    private Boolean ret;

    @XmlElement(name = "data")
    private Object data;

    @XmlElement
    private Integer code;

    @XmlElement
    private String message;

    public Result() {
        this(false, null);
    }

    public Result(Object data) {
        this(true, data);
    }

    public Result(boolean ret, Object data) {
        this.ret = ret;
        this.data = data;
    }

    public Result(boolean ret, Integer code, String message) {
        this.ret = ret;
        this.code = code;
        this.message = message;
    }

    public Boolean getRet() {
        return ret;
    }

    public void setRet(Boolean ret) {
        this.ret = ret;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
