package cn.edu.swufe.lawschool.internship.android.bussiness.common.base;

import cn.edu.swufe.lawschool.internship.android.bussiness.common.exception.InternshipException;
import cn.edu.swufe.lawschool.internship.android.bussiness.common.exception.CommonExceptionType;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2017年03月07
 * <p>Title:       基础type</p>
 * <p>Description: 基础type</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class BaseType implements Serializable {
    private Integer code;

    private String desc;

    protected BaseType (Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public BaseType () {

    }

    public static <T> List<T> getValues (Class<T> typeClass) {
        Field fields[] = typeClass.getDeclaredFields();
        List<T> values = new ArrayList<T>();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers())) {
                try{
                    T v = (T) f.get(typeClass);
                    if (v instanceof BaseType) {
                        values.add(v);
                    }
                } catch (IllegalArgumentException e) {

                } catch (IllegalAccessException e) {

                }
            }
        }
        return values;
    }

    public static <T extends BaseType> T parse (List<T> values, Integer code) {
        if (code == null) {
            return null;
        }
        for (T v : values) {
            if (v.getCode().intValue() == code) {
                return v;
            }
        }
        String message = String.format("%d not found in type:%s", code, values.get(0).getClass().getName());
        throw new InternshipException(CommonExceptionType.TYPE_NOT_FOUND, message);
    }

    public boolean equals (BaseType baseType) {
        if (baseType == null) {
            return false;
        }
        if (this == baseType) {
            return true;
        } else {
            return this.getCode().equals(baseType.getCode());
        }
    }

    public final Integer getCode () {
        return code;
    }

    public void setCode (Integer code) {
        this.code = code;
    }

    public String getDesc () {
        return desc;
    }

    public void setDesc (String desc) {
        this.desc = desc;
    }

    public String toString () {
        return getDesc();
    }
}
