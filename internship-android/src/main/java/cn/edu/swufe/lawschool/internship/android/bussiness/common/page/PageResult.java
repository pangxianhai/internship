package cn.edu.swufe.lawschool.internship.android.bussiness.common.page;

/**
 * Created on 2017年05月03
 * <p>Title:       分页结果</p>
 * <p>Description: 分页结果</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class PageResult<T> {
    T data;

    Page page;

    public T getData () {
        return data;
    }

    public void setData (T data) {
        this.data = data;
    }

    public Page getPage () {
        return page;
    }

    public void setPage (Page page) {
        this.page = page;
    }
}
