package cn.edu.zju.socialnetwork.configuration;


import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Order(2)
//@WebFilter(filterName = "cookieFilter", urlPatterns = "/*", initParams = {@WebInitParam(name = "EXCLUDED_PAGES", value = "/user/validemail;/user/login")})
public class CookieFilter implements Filter {

    private String excludedPages;
    private String[] excludedPageArray;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludedPages = filterConfig.getInitParameter("EXCLUDED_PAGES");
        if (null != excludedPages && excludedPages.length() != 0) { // 例外页面不为空
            excludedPageArray = excludedPages.split(String.valueOf(';'));
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("=============CookieFilter===================");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        boolean isExcludedPage = false;
        for (String page : excludedPageArray) {// 遍历例外url数组
            // 判断当前URL是否与例外页面相同
            System.out.println("接收到的url："+request.getServletPath());
            if (request.getServletPath().equals(page)) {
                System.out.println(page + ", you're excluded.");
                isExcludedPage = true;
                break;
            }
        }

        // 对于需要被过滤的url
        // 没有loginAccount对应的cookie则拒绝通过
        if (!isExcludedPage) {
            Cookie[] cookies = request.getCookies();
            if (cookies == null || cookies.length == 0) {
                System.out.println("没有cookie");
                response.setStatus(477);
                return;
            } else {
                boolean status = false;
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("loginAccount")) {
                        status = true;
                    }
                }
                if (status == false) {
                    response.setStatus(477);
                    return;
                }
            }
        }
        System.out.println("=============passed===================");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        this.excludedPages = null;
        this.excludedPageArray = null;
    }
}
