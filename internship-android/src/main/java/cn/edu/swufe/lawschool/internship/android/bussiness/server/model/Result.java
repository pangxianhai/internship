package cn.edu.swufe.lawschool.internship.android.bussiness.server.model;

import java.io.Serializable;

/**
 * Created on 2015年09月25
 * <p>Title:       接口返回结果格式</p>
 * <p>Description: 接口返回结果格式</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class Result<T> implements Serializable {

    private Boolean ret;

    private Object data;

    private Integer code;

    private String message;

    public Result () {
        this(false, null);
    }

    public Result (Object data) {
        this(true, data);
    }

    public Result (boolean ret, Object data) {
        this.ret = ret;
        this.data = data;
    }

    public Result (boolean ret, Integer code, String message) {
        this.ret = ret;
        this.code = code;
        this.message = message;
    }

    public Boolean getRet () {
        return ret;
    }

    public void setRet (Boolean ret) {
        this.ret = ret;
    }

    public T getData () {
        return (T) data;
    }

    public void setData (Object data) {
        this.data = data;
    }

    public Integer getCode () {
        return code;
    }

    public void setCode (Integer code) {
        this.code = code;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage (String message) {
        this.message = message;
    }
}
