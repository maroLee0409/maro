package com.global.event.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.global.action.Action;
import com.global.action.View;
import com.global.event.model.Event;
import com.global.event.model.EventDAO;
import com.google.gson.Gson;

public class EventListApiAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        EventDAO dao = EventDAO.getInstance();
        
        String eventType = request.getParameter("eventType");  // 이벤트 타입 (banner, coupon 등)
        List<? extends Event> eventList = dao.getEventList(eventType);  // 이벤트 타입에 따라 다르게 처리
        
        // JSON 형식으로 이벤트 리스트 응답
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        String jsonEventList = gson.toJson(eventList);  // eventList를 JSON으로 변환
        response.getWriter().write(jsonEventList);  // JSON 응답 반환
        
        return null;
    }
}
