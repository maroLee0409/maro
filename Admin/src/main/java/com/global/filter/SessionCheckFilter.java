package com.global.filter;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.global.user.model.UsersDTO;

@WebFilter(urlPatterns = {"*.do", "*.go"})
public class SessionCheckFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(SessionCheckFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpSession session = httpRequest.getSession(false);  // 세션이 없으면 null 반환

        // 로그인 페이지 또는 로그인 처리 요청은 필터에서 제외
        String requestURI = httpRequest.getRequestURI();
        if (requestURI.endsWith("/index.jsp") || requestURI.endsWith("/userLogin.do")) {
            logger.info("해당 페이지의 세션 필터를 제외합니다.");
            chain.doFilter(request, response);
            return;
        }

        // 세션 확인
        if (session != null) {
            // 세션에 저장된 모든 속성 검사
            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                Object attributeValue = session.getAttribute(attributeName);
                
                logger.info("세션 속성 확인: {} = {}", attributeName, attributeValue);
            }

            // 예시로, 특정 속성들에 대해 별도 검사를 할 수 있음
            Object uploadedFiles = session.getAttribute("uploadedFiles");
            // 세션 검사
            if (session != null && session.getAttribute("user") != null) {
                UsersDTO user = (UsersDTO) session.getAttribute("user");
                logger.info("user 세션이 유지되고 있습니다: userNo={}, userId={}, name={}, email={}, userType={}", 
                        user.getUserNo(), user.getUserId(), user.getName(), user.getEmail(), user.getUserType());
            }else {
            	logger.info("user 세션이 존재 하지 않습니다.");
            }

            if (uploadedFiles == null) {
                logger.info("업로드된 파일 정보가 없습니다.");
            } else {
                logger.info("업로드된 파일 정보가 세션에 존재합니다: {}", uploadedFiles);
            }

        }
        // 필터 체인을 계속해서 진행
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
