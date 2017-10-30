package cn.edu.swufe.lawschool.common.util;

import cn.edu.swufe.lawschool.mybatis.paginator.Order;
import cn.edu.swufe.lawschool.mybatis.paginator.Page;

import java.util.List;

/**
 * Created on 2015年11月12
 * <p>Title:       Page工具类</p>
 * <p>Description: Page工具类</p>
 * <p>Copyright:   Copyright (c) 2015</p>
 * @author 庞先海
 * @version 1.0
 */
public class PageUtil {
    public static int PAGE_SIZE = 10;

    /**
     * 过滤 currentPage 如果currentPage <= 0 return 1
     * @param currentPage
     * @return
     */
    public static int filter (Integer currentPage) {
        if (currentPage == null || currentPage.intValue() <= 0) {
            return 1;
        } else {
            return currentPage.intValue();
        }
    }

    public static Page buildPage (Integer currentPage, Integer pageSize) {
        int _currentPage = filter(currentPage);
        int _pageSize = filter(pageSize);
        if (_pageSize == 1) {
            _pageSize = PAGE_SIZE;
        }
        return new Page(_currentPage, _pageSize);
    }

    public static Page buildPage (Integer currentPage, Integer pageSize, String orderSegment) {
        Page page = buildPage(currentPage, pageSize);
        page.setOrders(Order.formString(orderSegment));
        return page;
    }
}
