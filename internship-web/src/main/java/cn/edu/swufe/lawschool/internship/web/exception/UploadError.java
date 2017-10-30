package cn.edu.swufe.lawschool.internship.web.exception;

import cn.edu.swufe.lawschool.internship.exception.ErrorType;

/**
 * Created on 2015年11月15
 * <p>Title:       上传错误类</p>
 * <p>Description: 上传错误类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class UploadError extends ErrorType {
    public final static ErrorType UPLOAD_FILE_EMPTY = new UploadError(90000, "上传的文件为空");
    public final static ErrorType UPLOAD_FILE_FAILED = new UploadError(90001, "上传的文件失败");
    public final static ErrorType UPLOAD_IMAGE_FORMAT = new UploadError(90002, "上传的文件格式不支持");

    protected UploadError(Integer code, String desc) {
        super(code, desc);
    }
}
