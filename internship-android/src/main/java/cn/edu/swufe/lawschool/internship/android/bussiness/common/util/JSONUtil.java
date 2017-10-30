package cn.edu.swufe.lawschool.internship.android.bussiness.common.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created on 2017年04月24
 * <p>Title:       json工具类</p>
 * <p>Description: json工具类</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @version 1.0
 */
public class JSONUtil {

    /**
     * json字符串转化为java对象
     * @return
     */
    public static <T> T fromJson (String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    /**
     * json元素转化为java对象
     * @return
     */
    public static <T> T fromJson (JsonElement element, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(element, clazz);
    }

    /**
     * json元素转化为List
     * @return
     */
    public static <T> List<T> fromJsonToList (JsonElement element, final Class<T> clazz) {
        Gson gson = new Gson();
        Type type = new ParameterizedType() {
            public Type[] getActualTypeArguments () {
                return new Type[] { clazz };
            }

            public Type getRawType () {
                return List.class;
            }

            public Type getOwnerType () {
                return null;
            }
        };
        return gson.fromJson(element, type);
    }

    /**
     * json字符串转化为java对象
     * @return
     */
    public static <T> T fromJson (String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    /**
     * java对象转化为json字符串
     * @return
     */
    public static String toJson (Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    static final String SECRET_ALL_KEY = "7rS!#9P!7fQXi3vuna5T2xSvDOfDUfHQ";
}
