package cn.edu.swufe.lawschool.mybatis.paginator;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.session.RowBounds;
import org.springframework.util.CollectionUtils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 2015年11月03
 * <p>Title:        分页功能</p>
 * <p>Description:  分页功能</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class Dialect {
    protected MappedStatement mappedStatement;
    protected Object parameterObject;
    protected Page page;
    protected BoundSql boundSql;
    protected List<ParameterMapping> parameterMappings;

    private String pageSQL;
    private String countSQL;

    public Dialect(MappedStatement mappedStatement, Object parameterObject, Page page) {
        this.mappedStatement = mappedStatement;
        this.parameterObject = parameterObject;
        this.page = page;
        init(mappedStatement, parameterObject, page);
    }

    public void init(MappedStatement mappedStatement, Object parameterObject, Page page) {

        boundSql = mappedStatement.getBoundSql(parameterObject);
        parameterMappings = new ArrayList<ParameterMapping>(boundSql.getParameterMappings());
        String sql = boundSql.getSql().trim();
        if (sql.endsWith(";")) {
            sql = sql.substring(0, sql.length() - 1);
        }
        pageSQL = sql;
        if (!CollectionUtils.isEmpty(page.getOrders())) {
            pageSQL = getSortString(sql, page.getOrders());
        }
        if (page.getLimit() != RowBounds.NO_ROW_LIMIT) {
            pageSQL =
                    getLimitString(pageSQL, page.getOffset(), page.getLimit());
        }
        countSQL = getCountString(sql);
    }


    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }

    public Object getParameterObject() {
        return parameterObject;
    }


    public String getPageSQL() {
        return pageSQL;
    }

    public String getCountSQL() {
        return countSQL;
    }


    /**
     * 将sql变成分页sql语句
     */
    protected String getLimitString(String sql, int offset, int limit) {
        throw new UnsupportedOperationException("paged queries not supported");
    }

    /**
     * 将sql转换为总记录数SQL
     * @param sql SQL语句
     * @return 总记录数的sql
     */
    protected String getCountString(String sql) {
        return "select count(1) from (" + sql + ") tmp_count";
    }

    /**
     * 将sql转换为带排序的SQL
     * @param sql SQL语句
     * @return 总记录数的sql
     */
    protected String getSortString(String sql, List<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return sql;
        }

        StringBuffer buffer =
                new StringBuffer("select * from (").append(sql).append(") temp_order order by ");
        for (Order order : orders) {
            if (order != null) {
                buffer.append(order.toString())
                        .append(", ");
            }

        }
        buffer.delete(buffer.length() - 2, buffer.length());
        return buffer.toString();
    }
}
