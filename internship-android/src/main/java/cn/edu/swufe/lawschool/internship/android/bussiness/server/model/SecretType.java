package cn.edu.swufe.lawschool.internship.android.bussiness.server.model;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.base.BaseType;

/**
 * Created on 2016年09月22
 * <p>Title:       签名类型</p>
 * <p>Description: 签名类型</p>
 * <p>Copyright:   Copyright (c) 2016</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class SecretType extends BaseType {
    public final static SecretType MD5 = new SecretType(100, "md5");

    public final static SecretType SHA = new SecretType(101, "sha");

    public final static SecretType SHA256 = new SecretType(102, "sha256");

    public final static SecretType DEFAULT = new SecretType(103, "default");

    SecretType (Integer code, String desc) {
        super(code, desc);
    }

}
