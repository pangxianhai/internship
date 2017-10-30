package cn.edu.swufe.lawschool.internship.android.bussiness.common.page;

/**
 * Created on 2017年05月03
 * <p>Title:       分页信息</p>
 * <p>Description: 分页信息</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class Page {
    private Integer currentPage;

    private Integer pageSize;

    private Integer totalRecord;

    private Integer totalPage;

    public Page () {
    }

    public Page (Integer currentPage, Integer pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public Integer getCurrentPage () {
        return currentPage;
    }

    public void setCurrentPage (Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize () {
        return pageSize;
    }

    public void setPageSize (Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRecord () {
        return totalRecord;
    }

    public void setTotalRecord (Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public Integer getTotalPage () {
        return totalPage;
    }

    public void setTotalPage (Integer totalPage) {
        this.totalPage = totalPage;
    }
}
