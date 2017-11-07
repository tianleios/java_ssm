package com.tianlei.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by tianlei on 2017/8/3.
 */
@WebFilter(filterName = "filter",urlPatterns = {"/*"})
public class ReqFilter implements Filter {


    public void init(FilterConfig filterConfig) throws ServletException {

        System.out.print("过滤器初始化");

    }


    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

//      servletResponse.setContentType("application/json;utf8");
        servletResponse.setContentType("application/json");
        servletResponse.setCharacterEncoding("utf-8");
        filterChain.doFilter(servletRequest,servletResponse);

    }


    public void destroy() {

    }
}
