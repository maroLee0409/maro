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
		<c:set var="pi" value="${count }" />
		<h3>${dto.getName() }상세내역 페이지</h3>
		<br> <br>

		<table>
			<tr>
				<th class="producter-content-th product-th-userNo">유저 번호</th>
				<td class="product-td">${dto.getUser_no() }</td>
			</tr>

			<tr>
				<th class="producter-content-th product-th-productNo">상품 번호</th>
				<td class="product-td">${dto.getProduct_no() }</td>
			</tr>

			<tr>
				<th class="producter-content-th product-th-categoryNo">카테고리 번호</th>
				<td class="product-td">${dto.getCategory_no() }</td>
			</tr>

			<tr>
				<th class="producter-content-th product-th-name">상품 이름</th>
				<td class="product-td">${dto.getName() }</td>
			</tr>

			<tr>
				<th class="producter-content-th product-th-info">상품 정보</th>
				<td class="product-td">${fn:replace(dto.getDescription(), newLine, br) }</td>
			</tr>
			
			<tr>
				<th class="producter-content-th product-th-img">상품 이미지</th>
				<td class="product-td"><img src="${contextPath }/${dto.getImage_url()}"></td>
			</tr>


			<tr>
				<th class="producter-content-th product-th-imf-info">상품 이미지 정보 ${dto.getImg_description() }</th>
				<td class="product-td">${fn:replace(dto.getImg_description(), newLine, br) }</td>
			</tr>

			<tr>
				<th class="producter-content-th product-th-price">상품 가격</th>
				<td class="product-td"><fmt:formatNumber value="${dto.getPrice() }"
						pattern="#,###" /></td>
			</tr>

			<tr>
				<th class="producter-content-th product-th-stock">상품 재고</th>
				<td class="product-td"><fmt:formatNumber value="${dto.getStock_quantity() }"
						pattern="#,###" /></td>
			</tr>


			<tr>
				<th class="producter-content-th product-th-views">상품 조회수</th>
				<td class="product-td"><fmt:formatNumber value="${dto.getViews() }"
						pattern="#,###" /></td>
			</tr>


			<tr>
				<th class="producter-content-th product-th-createAt">상품 등록일</th>
				<td class="product-td">${dto.getCreated_at() }</td>
			</tr>


			<tr>
				<th class="producter-content-th product-th-updateAt">상품 수정일</th>
				<td class="product-td">${dto.getUpdated_at() }</td>
			</tr>


			<tr>
				<th class="producter-content-th product-th-status">상품 상태</th>
				<td class="product-td">
					<c:choose>
						<c:when test="${dto.getIs_deleted() == 'N'}">판매중</c:when>
						<c:otherwise>삭제됨</c:otherwise>
					</c:choose>
				</td>
			</tr>


			<tr>
				<th class="producter-content-th product-th-sales">상품 판매량</th>
				<td class="product-td"><fmt:formatNumber value="${dto.getTotal_sales() }"
						pattern="#,###" /></td>
			</tr>




			<c:if test="${empty dto }">
				<tr>
					<td colspan="13" align="center">
						<h3>해당하는 게시글이 없습니다.</h3>
					</td>
				</tr>
			</c:if>
		</table>
		<br> <input class="btn" type="button" value="상품수정"
			onclick="location.href='productModify.do?no=${dto.getProduct_no() }&currentPage=${pi}&status=${status}'">
		&nbsp;&nbsp; <input class="btn" type="button" value="상품삭제"
			onclick="location.href='productDelete.do?no=${dto.getProduct_no() }&currentPage=${pi}&status=${status}'">
		&nbsp;&nbsp; <input class="btn" type="button" value="전체목록"
			onclick="location.href='productList.do?currentPage=${pi}&status=${status}&subtitle='"><br>
		<br> <input class="btn" type="button" value="찜하기" onclick="location.href='cart_insert.do?no=${dto.getProduct_no() }&num=${sessionScope.user.userNo}&user_no=${dto.getUser_no() }'"> 
		<input class="btn" type="button" value="구매하기"
			onclick="location.href='prodcut_buy_page.do?no=${ dto.getProduct_no() }&num=${sessionScope.user.userNo}'">

	</div>
</body>
</html>