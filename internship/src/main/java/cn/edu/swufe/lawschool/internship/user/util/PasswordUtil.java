package cn.edu.swufe.lawschool.internship.user.util;

import com.xavier.commons.util.encrypt.EncodeUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created on 2017年03月14
 * <p>Title:       密码工具</p>
 * <p>Description: 密码工具</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class PasswordUtil {

    private final static String CHARSET = "UTF-8";

    public static String passwordHash (String userName, String password) {
        byte[] passwordByte = passwordHash(userName, password, "SHA-256", CHARSET);
        return EncodeUtil.base64Encode(passwordByte);
    }

    private static byte[] passwordHash (String password, String salt, String algorithm, String charsetName) {
        if (password == null || algorithm == null) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm.trim());
            if (salt != null) {
                md.update((salt.toLowerCase() + ":" + password).getBytes(charsetName));
                byte[] bys = md.digest();
                md.reset();
                md.update(password.getBytes(charsetName));
                md.update(bys);
            } else {
                md.update(password.getBytes(charsetName));
            }
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
