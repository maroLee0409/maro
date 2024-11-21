<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>이벤트 상세보기</title>
<link rel="stylesheet" href="${contextPath}/resources/event/css/event.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
// 이벤트 삭제
function deleteEvent(eventNo) {
    if (confirm("정말 이 이벤트를 삭제하시겠습니까?")) {
        location.href = 'eventDeleteOk.do?eventNo=' + eventNo + '&currentPage=${currentPage}';
    }
}

// 이벤트 삭제 취소
function restoreEvent(eventNo) {
    if (confirm("정말 이 이벤트의 삭제를 취소하시겠습니까?")) {
        location.href = 'eventRestoreOk.do?eventNo=' + eventNo + '&currentPage=${currentPage}';
    }
}

// 이벤트 완전 삭제
function realDeleteEvent(eventNo) {
    if (confirm("정말 이 이벤트를 완전히 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.")) {
        location.href = 'eventRealDeleteOk.do?eventNo=' + eventNo + '&currentPage=${currentPage}';
    }
}
</script>

</head>
<body>
    <div class="content">
        <!-- 이벤트 이름 -->
        <h2 class="content-title">${event.name}</h2>

        <!-- 작성자 및 작성/수정일 정보 -->
        <div class="content-info">
            <span>이벤트 상태: ${event.status}</span>
            <span>조회수: ${event.views}</span>
            <c:choose>
                <c:when test="${not empty event.updatedAt}">
                    <span>수정일: <fmt:formatDate value="${event.updatedAt}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                </c:when>
                <c:otherwise>
                    <span>작성일: <fmt:formatDate value="${event.createdAt}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- 이벤트 날짜 -->
        <div class="event-dates">
            <span>시작일: <fmt:formatDate value="${event.startDate}" pattern="yyyy-MM-dd" /></span>
            <span>종료일: <fmt:formatDate value="${event.endDate}" pattern="yyyy-MM-dd" /></span>
        </div>

        <!-- 본문 내용 -->
        <div class="content-body">
            <p>${event.description}</p>
        </div>

        <!-- 이벤트 이미지 처리 -->
        <c:if test="${not empty event.eventImage}">
            <div class="event-image">
                <img src="${event.eventImage.filePath}" alt="${event.eventImage.fileName}" />
            </div>
        </c:if>

        <!-- 쿠폰 이벤트 정보 -->
        <c:if test="${eventType == 'coupon'}">
            <div class="coupon-info">
                <h3>쿠폰 정보</h3>
                <p>쿠폰 코드: ${event.couponCode}</p>
                <p>할인 금액: ${event.discountAmount}</p>
                <p>할인율: ${event.discountPercent}%</p>
                <p>쿠폰 만료일: <fmt:formatDate value="${event.expiryDate}" pattern="yyyy-MM-dd"/></p>
            </div>
        </c:if>

        <!-- 배너 이벤트 정보 -->
        <c:if test="${eventType == 'banner'}">
            <div class="banner-info">
                <h3>배너 정보</h3>
                <p>배너 링크: <a href="${event.linkUrl}" target="_blank">${event.linkUrl}</a></p>
                <p>배너 표시 시작일: <fmt:formatDate value="${event.displayStartDate}" pattern="yyyy-MM-dd"/></p>
                <p>배너 표시 종료일: <fmt:formatDate value="${event.displayEndDate}" pattern="yyyy-MM-dd"/></p>
            </div>
        </c:if>
		
        <!-- 뒤로가기 버튼 -->
        <button type="button" onclick="location.href='eventList.do?status=${status}&currentPage=${currentPage}'" class="btn btn_space_tb">뒤로가기</button>
        
        <!-- 수정하기 및 삭제하기 버튼: 관리자만 가능 -->
        <c:if test="${sessionScope.user.userType == 'ADMIN'}">
            <button type="button" onclick="location.href='eventUpdateForm.do?eventNo=${event.eventNo}&currentPage=${currentPage}'" class="btn btn_space_tb">수정하기</button>
            
            <!-- status 상태에 따라 다른 버튼 표시 -->
            <c:choose>
                <c:when test="${event.status == 'ENDED'}">
                    <!-- 만료된 이벤트의 경우: 삭제 취소 또는 완전 삭제 버튼 -->
                    <button type="button" onclick="restoreEvent(${event.eventNo})" class="btn btn_space_tb">삭제 취소</button>
                    <button type="button" onclick="realDeleteEvent(${event.eventNo})" class="btn btn_space_tb">완전 삭제하기</button>
                </c:when>
                <c:otherwise>
                    <!-- 삭제되지 않은 이벤트의 경우: 삭제 버튼 -->
                    <button type="button" onclick="deleteEvent(${event.eventNo})" class="btn btn_space_tb">삭제하기</button>
                </c:otherwise>
            </c:choose>
        </c:if>
    </div>
    
</body>
</html>
