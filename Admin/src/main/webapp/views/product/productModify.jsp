<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${contextPath }/resources/product/css/product.css"
	rel="stylesheet" type="text/css" />
<title>Insert title here</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }

        .content {
        	width: 1000px;
            max-width: 1250px;
            margin: 50px auto;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        h3 {
            margin-bottom: 20px;
            color: #333;
            font-size: 28px;
            text-align: center;
        }

    </style>
</head>
<body>
	<div class="content">
		<c:set var="dto" value="${Modify }" />
		<c:set var="list" value="${CategoryList }"/>

		<h3>${dto.getName() }수정 페이지</h3>
		<br> <br>

		<form action="<%=request.getContextPath()%>/productModifyOk.do" method="post" 
		enctype="multipart/form-data">
		<input type="hidden" name="product_no" value="${dto.getProduct_no() }">
		<%-- <input type="hidden" name="cerated_at" value=${dto.getCreated_at() }> --%>
		<input type="hidden" name="user_no" value="${sessionScope.user.userNo}">
			<div class="form-group">
					<label for="category_no">카테고리 번호</label>
					<select name="category_no">
							<c:if test="${empty CategoryList }">
								<option value="">:::카테고리 코드 없음:::</option>
							</c:if>

							<c:if test="${!empty CategoryList }">
								<c:forEach items="${CategoryList }" var="category_dto">
									<option value="${category_dto.getCategory_No() }">
										${category_dto.getName() } (${ category_dto.getCategory_No() })</option>
								</c:forEach>
							</c:if>
					</select>
				</div>
				<div class="form-group">
					<label for="product_name">상품 이름</label>
					<input type="text" name="product_name" required value="${dto.getName() }">
				</div>
				<div class="form-group">
					<label for="description">상품 정보</label>
					<textarea id="content" rows="8" cols="22" name="description"
							value="${dto.getDescription() }"></textarea>
				</div>
				<div class="form-group">
					<label for="image_url">상품 이미지</label>
					<input type="file" name="image_url">
				</div>
				<div class="form-group">
					<label for="img_description">상품 이미지 정보</label>
					<textarea id="content" rows="8" cols="22" name="img_description"
							value="${dto.getImg_description() }"></textarea>
				</div>
				<div class="form-group">
					<label for="product_price">상품 가격</label>
					<input type="number"  min="0" name="product_price" required>
				</div>
					
				<div class="form-group">
					<label for="stock_quantity">상품 재고</label>
					<input name="stock_quantity" type="number" min="0" required>
				</div>

				<div class="form-group">
					<label for="product_state"> 상태</label>
					<select name="product_state">
						<option value="N">제품 수정</option>
						<option value="Y">제품 삭제</option>
					</select>
				</div>
				
			<table>																				
				<tr>
					<td class="table_bottom button categoryInsertBtn" colspan="3">
					<button type="button" onclick="location.href='productList.do?status=${status}&currentPage=${currentPage}'" class="btn btn_space_tb">뒤로가기</button>
						<input class="btn" type="submit" value="카테고리 수정"> 
						<input class="btn" type="reset" value="초기화">
					</td>
				</tr>
			</table>

			<br>



		</form>



	</div>
</body>
</html>