package cn.edu.swufe.lawschool.mybatis.paginator;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * Created on 2015年11月03
 * <p>Title:        mysql分页功能</p>
 * <p>Description:  mysql分页SQL拼接</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class MySQLDialect extends Dialect {
    /**
     * 将sql变成分页sql语句
     */
    public MySQLDialect(MappedStatement mappedStatement, Object parameterObject, Page page) {
        super(mappedStatement, parameterObject, page);
    }


    protected String getLimitString(String sql, int offset, int limit) {
        StringBuffer buffer = new StringBuffer(sql.length() + 20).append(sql);
        buffer.append(" limit " + offset + ", " + limit + "");
        return buffer.toString();
    }
}
