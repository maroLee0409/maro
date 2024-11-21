<%@page import="com.global.event.model.Event"%>
<%@ page import="com.global.utils.StringUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이벤트 관리 페이지</title>
<!-- 서버 측 변수를 HTML에서 정의 -->
<script type="text/javascript">
	const contextPath = '${contextPath}';
	const mapping = 'detailForm.do'; // 이벤트 타입에 따라 다른 URL로 변경 가능
	const currentPage = '${pi.currentPage}';
	const eventType = '${eventType}';
	const status = '${status}'; // 추가로 사용될 수 있는 status 변수
</script>

<!-- 외부 JavaScript 파일 호출 -->
<script src="${contextPath}/resources/common/js/goToDetailPage.js" type="text/javascript"></script>

</head>
<body>

	<div class="title">
	    <h1>${location.title}</h1> <!-- 동적 제목 출력 -->
	    ${location.displayBreadcrumb()} <!-- BreadCrumb 경로 출력 -->
	</div>

<div align="center">
    <div class="spacer"></div>
	<table border="1">
	    <tr>
	        <td class="table_header button" colspan="9" align="right">
	        	<c:choose>
	        		<c:when test="${eventType == 'BANNER'}">
	        			전체 베너 이벤트 수 : ${count}
	        		</c:when>
	        		<c:when test="${ eventType == 'COUPON'}">
	        			전체 쿠폰 이벤트 수 : ${count}	
	        		</c:when>
	        		<c:otherwise>
	        			전체 이벤트 수 : ${count}	
	        		</c:otherwise>
	        	</c:choose>
	        </td>
	    </tr>
	    <tr>
	        <th>No.</th> 
	        <th>이벤트 이름</th> 
	        <th>이벤트 내용</th>	        
	        <th>조회수</th>
	        <th>시작 날</th>
	        <th>끝나는 날</th>
	        <th>생성일</th>
	        <th>수정일</th>
	        <th>이벤트 만료 여부</th>
	        <th>이벤트 타입</th>
	    </tr>
	    <c:choose>
	        <c:when test="${not empty list}">
	            <c:forEach items="${list}" var="event">
					<tr class="trlist" data-id="${event.eventNo}" data-event-type="${event.eventType}">
						<td>${event.eventNo}</td>
						<td>${event.name}</td>
						<td>${StringUtils.stripHtml(event.description)}</td>
						<td>${event.views }</td>
						<td> <fmt:formatDate value="${event.startDate}" pattern="yyyy-MM-dd" /> </td>
						<td> <fmt:formatDate value="${event.endDate}" pattern="yyyy-MM-dd" /> </td>
						<td> <fmt:formatDate value="${event.createdAt}" pattern="yyyy-MM-dd" /> </td>
						<td> <fmt:formatDate value="${event.updatedAt}" pattern="yyyy-MM-dd" /> </td>
						<td> ${event.status } </td>
						<td> ${event.eventType} </td>
					</tr>
				</c:forEach>
	        </c:when>
	        <c:otherwise>
	            <tr>
	                <td colspan="10" align="center">
	                    <h3>이벤트가 존재하지 않습니다.</h3>
	                </td>
	            </tr>
	        </c:otherwise>
	    </c:choose>  
	    <tr>
	        <td class="table_bottom button" colspan="10" align="center">
	        	<!-- 로그인한 유저만 이벤트 등록 버튼 보이기 -->
            	<c:if test="${not empty sessionScope.user and sessionScope.user.userType == 'ADMIN'}">
					<input type="button" class="btn" value="이벤트 등록" onclick="location.href='eventInsertForm.do'">
	            </c:if>
	        </td>
	    </tr>
	</table>
</div>

</body>
</html>
