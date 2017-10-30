package cn.edu.swufe.lawschool.internship.server;

import com.xavier.rop.annotation.SecretType;
import com.xavier.rop.util.RopUtil;

import java.util.Map;

/**
 * Created on 2016年10月11
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class RopTestCommon {

    public static Map<String, Object> sign(Map<String, Object> paramMap) {
        String appId = "8537654264498689";
        String appSecret = "D8D000AFEE26ED0A63124A7A03A06245";
        paramMap.put("appId", appId);
        String sign = RopUtil.sign(paramMap, appSecret, SecretType.MD5);
        paramMap.put("sign", sign);
        return paramMap;
    }
}
