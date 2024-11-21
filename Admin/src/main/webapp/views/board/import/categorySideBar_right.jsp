<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fm" %>
<div class="sidebar">
    <!-- 기존 카테고리 유지 -->
    <!-- 실시간 뉴스 위젯 -->
    <div class="news-widget">
        <h3>실시간 뉴스</h3>
        <iframe src="https://rss.cnn.com/rss/edition.rss" width="100%" height="200"></iframe>
    </div>

    <!-- 시계 및 캘린더 위젯 -->
    <div class="clock-widget">
        <h3>현재 시간</h3>
        <iframe src="https://free.timeanddate.com/clock/i8vohsff/n1980/tlin/fn6/fs16/fcfff/tct/pct/ftb/th2" frameborder="0" width="150" height="50"></iframe>
    </div>

    <!-- QR 코드 생성기 -->
    <div class="qr-code-widget">
        <h3>이 페이지를 공유하기</h3>
        <img src="https://api.qrserver.com/v1/create-qr-code/?data=https://yourwebsite.com&size=100x100" alt="QR 코드">
    </div>
</div>
