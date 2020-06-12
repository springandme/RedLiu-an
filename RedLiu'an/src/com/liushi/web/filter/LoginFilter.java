package com.liushi.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName LoginFilter
 * @Description 登录验证的过滤器
 * @Author liushi
 * @Date 2020/4/11 17:13
 * @Version V1.0
 **/
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //0.强制转换
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        //1.获得资源请求路径
        String uri = req.getRequestURI();
        //2.判断是否包含登录相关资源的路径,要注意排除 css/js/图片/验证码等资源
        if (uri.contains("/login.jsp") || uri.contains("/loginServlet") || uri.contains("/css/") || uri.contains("/js" +
                "/") || uri.contains("/fonts/") || uri.contains("/checkCodeServlet")) {
            //包含,用户就是就是想登录,放行
            filterChain.doFilter(req, servletResponse);
        } else {
            //不包含,需要验证用户信息是否登录
            //3.从session中获取user  loginServlet中存入session数据user 如果不排除loginServlet,user为null
            Object user = req.getSession().getAttribute("user");
            if (user != null) {
                //登录了。放行
                filterChain.doFilter(req, servletResponse);
            } else {
                //没有登陆,跳转登录页面
                req.setAttribute("login_msg", "你尚未登录,请登录");
                req.getRequestDispatcher("/login.jsp").forward(req, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {

    }
}
