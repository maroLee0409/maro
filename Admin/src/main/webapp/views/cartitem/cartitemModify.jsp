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
		<c:set var="modify" value="${Modify }" />
		<h3>장바구니 상품 수정</h3>
		<br><br>
		<form method="post" action="cartItem_modify_ok.do">
			<input type="hidden" name="no" value="${ modify.user_no }"> 
			<input type="hidden" name="cartItem_no" value="${ modify.cartItem_no }"> 
			<table  width="150">
				<tr>
					<th>순번</th>
					<td>
						${ modify.cartItem_no }
					</td>
				</tr>
				
				<tr>
					<th>상품번호</th>
					<td>
						${ modify.product_no }
					</td>
				</tr>
				
				<tr>
					<th>상품명</th>
					<td>
						${ modify.product_name }
					</td>
				</tr>
				
				<tr>
					<th>수량</th>
					<td>
						<input name="quantity" value="${ modify.quantity }" > 개
					</td>
				</tr>
				
				<tr>
					<th>가격</th>
					<td>
					<fmt:formatNumber value="${ modify.product_price }"/>원 
					</td>
				</tr>
			</table>
			<br>
			<input  class="btn" type="submit"  value="글수정">&nbsp;&nbsp;
			<input  class="btn" type="reset" value="다시작성">&nbsp;&nbsp;
			<input  class="btn" type="button" value="되돌아가기" onclick="history.back()"> 
		</form>
	</div>
</body>
</html>