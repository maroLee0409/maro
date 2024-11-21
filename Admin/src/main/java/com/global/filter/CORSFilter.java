package com.global.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletResponse res = (HttpServletResponse) response;

        // CORS 허용 헤더 추가
        res.setHeader("Access-Control-Allow-Origin", "http://localhost:8586");
        res.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");  // 허용하는 HTTP 메서드
        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");  // 허용하는 요청 헤더
        res.setHeader("Access-Control-Allow-Credentials", "true");

        // 필터 체인으로 요청을 넘김
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
