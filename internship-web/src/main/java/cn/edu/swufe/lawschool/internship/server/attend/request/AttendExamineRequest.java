package cn.edu.swufe.lawschool.internship.server.attend.request;

import com.xavier.rop.request.AbstractRopRequest;
import com.xavier.rop.validation.annotation.Length;
import com.xavier.rop.validation.annotation.NotBlank;
import com.xavier.rop.validation.annotation.NotNull;

/**
 * Created on 2016年10月24
 * <p>Title:       审核签到request</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AttendExamineRequest extends AbstractRopRequest {

    @NotBlank
    @Length(max = 100)
    String attendRecordDestId;

    @NotNull
    Integer attendStatusCode;

    public String getAttendRecordDestId() {
        return attendRecordDestId;
    }

    public void setAttendRecordDestId(String attendRecordDestId) {
        this.attendRecordDestId = attendRecordDestId;
    }

    public Integer getAttendStatusCode() {
        return attendStatusCode;
    }

    public void setAttendStatusCode(Integer attendStatusCode) {
        this.attendStatusCode = attendStatusCode;
    }
}
