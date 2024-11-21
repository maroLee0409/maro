package com.global.user.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;

public class UserLogoutAction implements Action {

	@Override
	public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 세션 무효화(로그아웃 처리)
        request.getSession().invalidate();
        
        // 응답 출력 스트림을 사용해 자바스크립트로 알림 및 리다이렉트
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<script>");
        out.println("alert('로그아웃 성공!!!');");
        // request.getContextPath()를 사용해 절대 경로로 리다이렉트
        out.println("location.href='" + request.getContextPath() + "/views/member/login.jsp';");
        out.println("</script>");
        
        out.close();
        
        // View를 반환하지 않음 (자바스크립트를 사용해 리다이렉트 처리)
        return null;
	}

}
