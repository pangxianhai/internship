package cn.edu.swufe.lawschool.internship.server.attend.request;

import com.xavier.rop.request.AbstractRopRequest;
import com.xavier.rop.validation.annotation.Length;
import com.xavier.rop.validation.annotation.NotBlank;

/**
 * Created on 2016年10月24
 * <p>Title:       删除签到参数</p>
 * <p>Description: 删除签到参数</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class AttendDeleteRequest extends AbstractRopRequest {

    @NotBlank
    @Length(max = 100)
    String attendRecordDestId;

    public String getAttendRecordDestId() {
        return attendRecordDestId;
    }

    public void setAttendRecordDestId(String attendRecordDestId) {
        this.attendRecordDestId = attendRecordDestId;
    }
}
