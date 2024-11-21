<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<h3>[${user.getName() }]님의 마이페이지</h3>
		
		<table border = "1" width = "auto">
		
			<c:set var="buyList" value="${list }"/>
			<tr>
				<td>주문번호</td> <td>회원번호</td> <td>주문날짜</td>
				<td>주문상태</td> <td>총 상품 금액</td> <td>주문 상세 정보</td>
			</tr>
			
			<c:if test="${!empty buyList }">
				<c:forEach items="${buyList }" var ="dto">
				
				<tr>
					<td>${dto.getOrder_no() }</td>
					<td>${dto.getUser_no() }</td>
					<td>${dto.getOrder_date() }</td>
					<td>${dto.getStatus() }</td>
					<td><fmt:formatNumber value="${dto.getTotal_amount() }"></fmt:formatNumber>원</td>
					<td>
						<input type="button" value="상세내역" onclick="location.href='content.do?user_no=${dto.getUser_no() }'">
					</td>
					
					<!-- 배송 완료인 고객만 리뷰 작성 -->
					<%-- <c:if test="${dto.getStatus().equals('DELIVERED') }">
					<td>
						<input type = "button" value = "리뷰 작성" onclick="location.href='productReviewinsert.do?user_no=${dto.getUser_no() }'">
					</td>
					</c:if>
					
					<c:if test="${!dto.getStatus().equals('DELIVERED') }">
						<td>리뷰X</td>
					</c:if> --%>
				</tr>
				
				</c:forEach>
			</c:if>
			
		</table>
		
	</div>
</body>
</html>