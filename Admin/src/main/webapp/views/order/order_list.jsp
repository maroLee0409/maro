<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	function showPopup() {
    alert("배송이 완료되지 않아 리뷰 작성이 불가능합니다.");
}
</script>
</head>
<body>
	<div align="center">
		<h3>주문 목록</h3>
		<table border="1" >
			<tr>
				<th>주문 번호</th>
				<th>주문 고객 번호</th>
				<th>주문 일자</th>
				<th>주문 상태</th>
				<th>총 주문 금액</th>
				<th>설 정</th>
				<th>리뷰</th>
			</tr>

			<c:if test="${not empty List }">
				<c:forEach items="${ List }" var="dto">
					<tr>
						<td><a href="${ contextPath }/orderitemcontent.do?no=${dto.getOrder_no() }">${dto.getOrder_no()}</a></td>
						<td>${dto.getUser_no()}</td>
						<td>${dto.getOrder_date()}</td>
					<c:if test="${dto.status eq 'PENDING'}">
						<td>배송 대기</td>
					</c:if>
					<c:if test="${dto.status eq 'SHIPPED'}">
						<td>배송 중</td>
					</c:if>
					<c:if test="${dto.status eq 'DELIVERED'}">
						<td>배송 완료</td>
					</c:if>
					<c:if test="${dto.status eq 'CANCELLED'}">
						<td>배송 취소</td>
					</c:if>
						<td><fmt:formatNumber value="${dto.getTotal_amount()}"></fmt:formatNumber>원</td>
					
						<td>
						<c:if test="${sessionScope.user.userType == 'ADMIN'}">
							<input class="btn" type="button" value="배송상태설정" onclick="location.href='orderModify.do?no=${dto.order_no}'">&nbsp;&nbsp;
						</c:if>
					</td> 
					
						<c:if test="${dto.status == 'DELIVERED' }">
							<td>
							<input class="btn" type="button" value="리뷰 작성" onclick="location.href='productReviewinsert.do'">
							</td>
						</c:if>
						<c:if test="${dto.status != 'DELIVERED' }">
							<td>
							<input class="btn" type="button" value="리뷰 작성" onclick="showPopup()">
							</td>
						</c:if>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty List }">
				<tr>
					<td colspan="5" align="center">
						<h3>상품 목록이 없습니다.</h3>
					</td>
				</tr>
			</c:if>

		</table>
	</div>


</body>
</html>