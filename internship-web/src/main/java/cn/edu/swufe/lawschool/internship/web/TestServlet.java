package cn.edu.swufe.lawschool.internship.web;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created on 2017年05月20
 * <p>Title:       [title描述]</p>
 * <p>Description: [类功能描述]</p>
 * <p>Copyright:   Copyright (c) 2017</p>
 * <p>Company:     号百信息服务有限公司</p>
 * <p>Department:  电子商务部</p>
 * @author 庞先海 pangxianhai@besttone.com.cn
 * @version 1.0
 */
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("get .....");
    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("post .....");
    }

    @Override
    public void destroy () {
        System.out.println("销毁.....");
    }

    @Override
    public void init (ServletConfig config) throws ServletException {
        System.out.println("test 初始化2.....");
    }

    @Override
    public void init () throws ServletException {
        System.out.println("test 初始化1.....");
    }
}
