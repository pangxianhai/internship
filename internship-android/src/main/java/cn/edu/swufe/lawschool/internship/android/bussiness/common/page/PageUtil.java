package cn.edu.swufe.lawschool.internship.android.bussiness.common.page;

import android.util.Log;
import cn.edu.swufe.lawschool.internship.android.SystemInfo;

/**
 * Created on 2017年05月03
 * <p>Title:       分页工具</p>
 * <p>Description: 分页工具</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class PageUtil {

    public static int PAGE_SIZE = 15;

    public static Page buildPage (Integer currentPage) {
        if (currentPage == null || currentPage <= 0) {
            currentPage = 1;
        }
        return new Page(currentPage, PAGE_SIZE);
    }

    public static Page nextPage (Page page) {
        if (page == null) {
            return buildPage(1);
        } else {
            page.setCurrentPage(page.getCurrentPage() + 1);
        }
        return page;
    }
}
