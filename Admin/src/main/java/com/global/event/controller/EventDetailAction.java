package com.global.event.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.global.action.Action;
import com.global.action.View;
import com.global.event.model.Event;
import com.global.event.model.EventDAO;
import com.global.utils.RequestParameterUtils;

public class EventDetailAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 이벤트 번호와 이벤트 타입을 파라미터로 받음
        int eventNo = Integer.parseInt(request.getParameter("no"));
        String eventType = RequestParameterUtils.parseString(request.getParameter("eventType"), "");
        
        // DAO에서 이벤트 정보 가져오기
        Event event = EventDAO.getInstance().getEventDetail(eventNo, eventType);
        
        // 이벤트 정보를 request에 저장
        request.setAttribute("event", event);
        request.setAttribute("eventType", eventType);
        
        // 상세 페이지로 이동
        return new View("main.go").setUrl("/views/event/eventDetailForm.jsp");
    }
}
