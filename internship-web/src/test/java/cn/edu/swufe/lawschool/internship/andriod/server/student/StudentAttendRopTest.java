package cn.edu.swufe.lawschool.internship.server.student;

import cn.edu.swufe.lawschool.internship.server.RopTestCommon;
import com.xavier.commons.util.HttpClientUtil;
import com.xavier.rop.annotation.CharsetName;
import com.xavier.rop.annotation.FormatType;
import com.xavier.rop.annotation.SecretType;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created on 2016年10月19
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class StudentAttendRopTest {

    public static void main(String args[]) {
        System.setProperty("catalina.base", "/Users/pangxianhai/www/chat/");
//        LoginRopTest.testLogin();
        testStudentAttendList();
    }

    public static void testStudentAttendList() {
        Map<String, Object> params = new TreeMap<String, Object>();
        params.put("method", "student.attend.list");
        params.put("version", "1.0");
        params.put("signType", SecretType.MD5.getCode());
        params.put("format", FormatType.JSON.getCode());
        params.put("sessionId", "af2b6e16e0de4d25b668b125fc9c5d52");
        params = RopTestCommon.sign(params);
        String rest = HttpClientUtil.sendGet("http://127.0.0.1:9090/route", params, CharsetName.CHARSET_NAME);
        System.out.println(rest);
    }
}
