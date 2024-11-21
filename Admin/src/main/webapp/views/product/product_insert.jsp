<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<link href="${contextPath }/resources/product/css/product.css"
	rel="stylesheet" type="text/css" />
<title>상품 등록</title>
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
		<h3>상품 등록</h3>

		<form action="<%=request.getContextPath()%>/productInsertOk.do" method="post"
			enctype="multipart/form-data">
		 <input type="hidden" name="user_no" value="${sessionScope.user.userNo}">
				<c:set var="catlist" value="${CategoryList }" />
			<div class="form-group">
				<label for="product_name">상품 이름</label>
				<input type="text" name="product_name" required>
			</div>
			<div class="form-group">
					<label for="category_no">카테고리 번호</label>
					<select name="category_no" required>
							<c:if test="${empty CategoryList }">
								<option value="">:::카테고리 코드 없음:::</option>
							</c:if>

							<c:if test="${!empty CategoryList }">
								<c:forEach items="${CategoryList }" var="dto">
									<option value="${dto.getCategory_No() }">
										${dto.getName() } (${ dto.getCategory_No() })</option>
								</c:forEach>
							</c:if>
					</select>
				</div>
				<div class="form-group">
					<label for="product_info">상품 정보</label>
					<textarea id="content" rows="8" cols="22" name="product_info"></textarea>
				</div>
				<div class="form-group">
				
					<label for="product_price">상품 가격</label>
				<input type="number" min="0" name="product_price"required>
				</div>
				<div class="form-group">
					<label for="stock_quantity">제고 수량</label>
					<input type="number" min="0" name="stock_quantity" required>
				</div>
				<div class="form-group">
					<label for="image_url">상품 이미지</label>
					<input type="file" name="image_url">
				</div>
				<div class="form-group">
					<label for="product_image_info">상품 이미지 정보</label>
					<textarea id="content" rows="8" cols="22" name="product_image_info"></textarea>
				</div>
			<table>
				<tr>
					<td colspan="2" align="center">
						<input class="btn"  type="submit" value="상품 등록"> 
						<input class="btn" type="reset" value="초기화">
					</td>
				</tr>
			</table>
		</form>
	</div>

</body>
</html>