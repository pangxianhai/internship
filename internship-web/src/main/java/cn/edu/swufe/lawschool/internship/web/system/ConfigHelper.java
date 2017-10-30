package cn.edu.swufe.lawschool.internship.web.system;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created on 2015年11月09
 * <p>Title:       配置读取</p>
 * <p>Description: 配置读取</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class ConfigHelper {

    private static final String CONFIG_PATH = "configurations.properties";

    private static ConfigHelper con = new ConfigHelper();

    Properties prop = new Properties();

    private ConfigHelper() {

        String path = this.getClass().getClassLoader().getResource(CONFIG_PATH).getPath();

        try {
            prop.load(new InputStreamReader(new FileInputStream(path), "UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(CONFIG_PATH + " not found");
        }
    }

    public static String getValue(String key) {
        return con.prop.getProperty(key);
    }

    public static void main(String args[]) throws Exception {
        System.out.println(getValue("cookie.domain"));
//        prop.load(ConfigHelper.class.getResourceAsStream("classpath*:db.properties"));
//        ConfigHelper.getValue("cookie.domain");
    }

}
