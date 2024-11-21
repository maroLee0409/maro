<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
request.setAttribute("newLine", "\n");
request.setAttribute("br", "<br>");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${contextPath }/resources/product/css/product.css"
	rel="stylesheet" type="text/css" />
<title>Insert title here</title>
</head>
<body>
	<div align="center">
		<c:set var="dto" value="${Content }" />
		<h3>${dto.getName() }구매 페이지</h3>
		<br> <br>

		<table>
			<tr>
				<th class="producter-content-th product-th-name">상품 이름</th>
				<td class="product-td">${dto.getName() }</td>
			</tr>
			<tr>
				<th class="producter-content-th product-th-img">상품 이미지</th>
				<td class="product-td"><img src="${contextPath }/${dto.getImage_url()}"></td>
			</tr>
			<tr>
				<th class="producter-content-th product-th-price">상품 가격</th>
				<td class="product-td"><fmt:formatNumber value="${dto.getPrice() }"
						pattern="#,###" /></td>
			</tr>
			<tr>
				
			<c:if test="${empty dto }">
				<tr>
					<td colspan="13" align="center">
						<h3>해당하는 게시글이 없습니다.</h3>
					</td>
				</tr>
			</c:if>
		</table>
		<br> 
		<form action="<%=request.getContextPath()%>/order_insert.do" method="post"enctype="multipart/form-data">
			<div>
			<input type="hidden" name="product_no" value="${dto.getProduct_no() }">
			<input type="hidden" name="user_no" value="${sessionScope.user.userNo}">
			<input type="hidden" name="price" value="${dto.getPrice() }">
			<input type="number" name="quantity">
			<input class="btn" type="submit" value="구매하기">
			</div>
		</form>
	</div>
</body>
</html>