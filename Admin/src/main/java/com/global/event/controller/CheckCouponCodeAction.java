package com.global.event.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.event.model.EventDAO;
import com.google.gson.JsonObject; // Gson의 JsonObject 클래스 사용

public class CheckCouponCodeAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 클라이언트에서 전달된 쿠폰 코드 가져오기
        String couponCode = request.getParameter("couponCode");
        
        // DAO를 사용하여 쿠폰 코드 중복 여부 확인
        boolean isDuplicate = EventDAO.getInstance().isCouponCodeExists(couponCode);
        
        // JSON 응답 생성 (Gson 사용)
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("isDuplicate", isDuplicate);
        
        // 응답 타입을 JSON으로 설정하고 결과 전송
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonResponse.toString());
        
        // View를 반환할 필요 없으므로 null 반환
        return null;
    }
}
