package com.global.event.controller;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.global.action.Action;
import com.global.action.View;
import com.global.board.model.BoardDAO;
import com.global.event.model.BannerEvent;
import com.global.event.model.CouponEvent;
import com.global.event.model.Event;
import com.global.event.model.EventDAO;
import com.global.utils.ScriptUtil;

public class EventInsertOkAction implements Action {

    @Override
    public View execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // 공통 이벤트 정보
        String eventName = request.getParameter("eventName");
        String eventDescription = request.getParameter("eventDescription");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        String eventType = request.getParameter("eventType");

        EventDAO dao = EventDAO.getInstance();
        
        // 공통 이벤트 정보 저장
        Event event = new Event();
        event.setName(eventName);
        event.setDescription(eventDescription);
        event.setStartDate(startDate);
        event.setEndDate(endDate);
        event.setEventType(eventType);

        // Event 테이블에 저장
        int eventNo = dao.insertEvent(event);
        int check = eventNo;
        // 선택된 이벤트 유형에 따른 추가 정보 저장
        if ("banner".equalsIgnoreCase(eventType)) {
            String linkUrl = request.getParameter("linkUrl");
            // 배너 이미지 업로드 로직 추가 필요
            BannerEvent bannerEvent = new BannerEvent();
            bannerEvent.setEventNo(eventNo);
            bannerEvent.setLinkUrl(linkUrl);
            
            check = dao.insertBannerEvent(bannerEvent);
            
        } else if ("coupon".equalsIgnoreCase(eventType)) {
            String couponCode = request.getParameter("couponCode");
            double discountAmount = Double.parseDouble(request.getParameter("discountAmount"));
            double discountPercent = Double.parseDouble(request.getParameter("discountPercent"));
            Date expiryDate = Date.valueOf(request.getParameter("expiryDate"));

            CouponEvent couponEvent = new CouponEvent();
            couponEvent.setEventNo(eventNo);
            couponEvent.setCouponCode(couponCode);
            couponEvent.setDiscountAmount(discountAmount);
            couponEvent.setDiscountPercent(discountPercent);
            couponEvent.setExpiryDate(expiryDate);

            check = dao.insertCouponEvent(couponEvent);
        }
        
		if (check > 0) {
			ScriptUtil.sendScript(response, "이벤트 등록 성공", "eventList.do");
		} else {
			ScriptUtil.sendScript(response, "이벤트 등록 실패!!!", null);
		}
        

        // 이벤트 목록으로 리다이렉트
        return null;
    }
}
