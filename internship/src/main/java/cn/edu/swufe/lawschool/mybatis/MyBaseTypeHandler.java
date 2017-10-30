package cn.edu.swufe.lawschool.mybatis;

import cn.edu.swufe.lawschool.common.base.BaseType;
import cn.edu.swufe.lawschool.common.exception.CommonException;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created on 2015年11月04
 * <p>Title:       扩展mybatis BaseTypeHandler</p>
 * <p>Description: 将BaseType code 写入数据 从数据库读取的code 转化为BaseType</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class MyBaseTypeHandler<T extends BaseType> extends BaseTypeHandler<T> {
    private List<T> values;

    private Class<T> tClass;

    public MyBaseTypeHandler(Class<T> type) {
        if (type == null)
            throw new IllegalArgumentException("Type argument cannot be null");
        values = BaseType.getValues(type);
        this.tClass = type;
    }

    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.getCode());
    }

    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int code = rs.getInt(columnName);
        if (rs.wasNull()) {
            return null;
        } else {
            return parse(code);
        }
    }

    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int code = rs.getInt(columnIndex);
        if (rs.wasNull()) {
            return null;
        } else {
            return parse(code);
        }
    }

    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int code = cs.getInt(columnIndex);
        if (cs.wasNull()) {
            return null;
        } else {
            return parse(code);
        }
    }

    private T parse(int code) {
        try {
            Class<T> t = this.tClass;
            return BaseType.parse(values, code);
        } catch (CommonException e) {
            //todo 日志
            return null;
        }
    }
}
