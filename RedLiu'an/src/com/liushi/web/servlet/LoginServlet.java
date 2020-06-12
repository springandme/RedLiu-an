package com.liushi.web.servlet;

import com.liushi.domain.Admin;
import com.liushi.service.AdminService;
import com.liushi.service.impl.AdminServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.设置编码
        req.setCharacterEncoding("utf-8");
        //2.获取数据
        String verifycode = req.getParameter("verifycode");
        //2.1获取用户填写验证码
        HttpSession session = req.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        //确保验证码一次性
        session.removeAttribute("CHECKCODE_SERVER");
        //3.验证码校验
        if (!checkcode_server.equalsIgnoreCase(verifycode)) {
            //验证码不正确
            //提示信息
            req.setAttribute("login_msg", "验证码错误!");
            //请求转发,跳转登录页面
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        //4.1 获得用户信息的的map集合
        Map<String, String[]> parameterMap = req.getParameterMap();
        //4.2 创建User对象
//        User user = new User();
        Admin admin = new Admin();
        //4.3封装User对象
        try {
//            BeanUtils.populate(user, parameterMap);
            BeanUtils.populate(admin, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //5.调用service中的UserServiceImpl类的方法 --需要自己写个login方法
        AdminService service = new AdminServiceImpl();
//        User loginUser = service.login(user);
        Admin loginUser = null;
        try {
            loginUser = service.login(admin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //6.判断是否登录成功
        if (loginUser != null) {
            //登录成功
            //将用户数据存入session
            session.setAttribute("user", loginUser);
            //重定向,跳转页面  重定向需要加上虚拟目录
            resp.sendRedirect(req.getContextPath() + "/html/index.html");
        } else {
            //登录失败
            //提示信息
            req.setAttribute("login_msg", "用户名或密码错误!");
            //请求转发,跳转登录页面
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
