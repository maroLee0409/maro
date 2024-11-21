<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"></script>
</head>
<body>
		<div align="center">

		<h3>주문 목록 상세 페이지</h3>
		<table border="1">
			<tr>
				<th>주문아이템 번호</th>
				<th>주문 번호</th>
				<th>상품 번호</th>
				<th>주문 갯수</th>
				<th>개당 금액</th>
				<th>설 정</th>
			</tr>

			<c:if test="${not empty Content }">
				<c:forEach items="${Content }" var="dto">
				<tr>
				  	<td>${dto.order_item_no }</td>
					<td>${dto.oreder_no }</td>
					<td>${dto.product_no }</td>
					<td>${dto.quantity }</td>
					<td><fmt:formatNumber value="${dto.price}"></fmt:formatNumber>원</td>
					<td>
						<c:if test="${sessionScope.user.userType == 'ADMIN'}">
							<input class="btn" type="button" value="주문목록삭제" onclick="location.href='orderitem_delete.do?no=${dto.oreder_no}'">
						</c:if>
					</td> 
				</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty Content}">
				<tr>
					<td colspan="5" align="center">
						<h3>상세 목록이 없습니다.</h3>
					</td>
				</tr>
			</c:if>

		</table>
		<br>
			 
			
	</div>

</body>
</html>