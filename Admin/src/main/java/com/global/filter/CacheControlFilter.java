package com.global.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletResponse;

// 만약 모든 정적 리소스에 대한 필터를 적용하고 싶으면 아래 주석 활성화 
/* @WebFilter("/*") */
@WebFilter("*.css")
public class CacheControlFilter extends HttpFilter implements Filter {
    
    private static final long serialVersionUID = 2488519212789114118L;

    public CacheControlFilter() {
        super();
    }

    public void destroy() {
        // 필터 소멸 시 동작
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
		/*
	        // 캐시 만료 시간을 1시간으로 설정 (3600초)
	        httpResponse.setHeader("Cache-Control", "max-age=3600, must-revalidate");
	        httpResponse.setHeader("Pragma", "cache");
	        httpResponse.setDateHeader("Expires", System.currentTimeMillis() + 3600 * 1000);
		*/
        httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setDateHeader("Expires", 0);

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
        // 필터 초기화 시 동작
    }
}
