<%@page import="com.global.product.model.ProductDTO"%>
<%@ page import="com.global.utils.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"
	import="java.util.ArrayList,java.util.List ,com.global.product.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${contextPath }/resources/product/css/product.css"
	rel="stylesheet" type="text/css" />
<title>상품 목록 페이지</title>
<script type="text/javascript">
/**
 * 
 */
 
 // JavaScript 함수: 클릭한 행의 ID를 가져와서 상세 페이지로 이동
function goToDetailPage(event) {
	 
	 
    const target = event.currentTarget;
    const No = target.getAttribute('data-id');
    const userType = target.getAttribute('data-user-type');
    const currentPage = ${pi.currentPage};  // pi.currentPage를 자바스크립트 변수로 설정
    const status = '${status}';
    const subtitle = '${param.subtitle}';
    
    
    
    
    if (No) {
        window.location.href = '${contextPath}/productContent.do?no=' + No + '&userType=' + userType + '&status=' + '${status}' + '&currentPage=' + '${pi.currentPage}';
    }
}

// 모든 행에 클릭 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('table tr[data-id]');

    rows.forEach(row => {
        row.addEventListener('click', goToDetailPage);
    });
});
</script>
</head>
<body>
	<div align="center">
		<h3>상품 목록 페이지</h3>
		<br> <br>
<!-- 메인 콘텐츠 -->
        <div class="main-content">
            <!-- 검색 바 추가 -->
			<c:import url="product/import/productFilter.jsp" />

		<table border="1" width="650" align="center">
			<tr>
				<th class="producter-th product-th-userNo">유저 번호</th>
				<th class="producter-th product-th-productNo">상품 번호</th>
				<th class="producter-th product-th-categoryNo">카테고리<br>번호</th>
				<th class="producter-th product-th-name">상품 이름</th>
				<th class="producter-th product-th-info">상품 정보</th>
				<th class="producter-th product-th-img">상품 이미지</th>
				<th class="producter-th product-th-imf-info">상품 이미지 정보</th>
				<th class="producter-th product-th-price">상품 가격</th>
				<th class="producter-th product-th-stock">상품 재고</th>
				<th class="producter-th product-th-views">상품 조회수</th>
				<th class="producter-th product-th-createAt">상품 등록일</th>
				<th class="producter-th product-th-updateAt">상품 수정일</th>
				<th class="producter-th product-th-status">상품 상태</th>
				<th class="producter-th product-th-sales">상품 판매량</th>

			</tr>
			<c:set var="list" value="${List }" />
			<c:if test="${!empty list }">
				<c:forEach items="${list }" var="dto">
					<tr class="hover-row" data-id="${dto.getProduct_no()}" data-user-type="${dto.getUser_no()}">
						<td class="product-td">${dto.getUser_no() }</td>
						<td class="product-td">${dto.getProduct_no()}</td>
						<td class="product-td">${dto.getCategory_no() }</td>
						<td class="product-td">${dto.getName() }</td>
						<td class="product-td">${dto.getDescription() }</td>
						<td class="product-td">
							<img class="product_img" src="${contextPath }/${dto.getImage_url()}" />
						</td>
						<td class="product-td">${dto.getImg_description() }</td>
						<td class="product-td"><fmt:formatNumber value="${dto.getPrice() }" pattern="#,###" /></td>
						<td class="product-td"><fmt:formatNumber value="${dto.getStock_quantity() }" pattern="#,###" />
							<c:if test="${dto.getStock_quantity() <= 10}">
							<p style="color:red;">재고 부족</p>
							</c:if>
						</td>
						<td class="product-td"><fmt:formatNumber value="${dto.getViews() }" pattern="#,###" /></td>
						<td class="product-td">${dto.getCreated_at() }</td>
						<td class="product-td">${dto.getUpdated_at() }</td>
						<td class="product-td">
						<c:choose>
							<c:when test="${dto.getIs_deleted() == 'N'}">정상</c:when>
								<c:otherwise>삭제됨</c:otherwise>
							</c:choose>
						</td>
						<td class="product-td"><fmt:formatNumber value="${dto.getTotal_sales() }" pattern="#,###" /></td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${empty list }">
				<tr class="no-hover-row">
					<td colspan="14" align="center">
						<h3>카테고리가 없습니다.</h3>
					</td>
				</tr>
			</c:if>
		</table>
		</div>
	</div>
</body>
</html>
</body>
</html>