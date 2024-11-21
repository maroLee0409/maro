<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
<%
	request.setAttribute("newLine", "\n");
	request.setAttribute("br", "<br>");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div align="center">
		<c:set var="pi" value="${count }"/>
		<c:set var="dto" value="${Content }" />

		<h3>${dto.getName() } 카테고리 상세내역 페이지</h3>
		<br>
		<br>

		<table border="1" width="400">
			<tr>
				<th>카테고리 번호</th>
				<td>${dto.getCategory_No() }</td>
			</tr>

			<tr>
				<th>카테고리 목록</th>
				<td>${dto.getName() }</td>	
			</tr>
			<tr>
				<th>카테고리 정보</th>
				<td>${fn:replace(dto.getDescription(), newLine, br) }</td>
			</tr>
			<tr>
				<th>카테고리 이미지</th>
				<td>
					<img src="${contextPath }/${dto.getImage_url()}">
				</td>
			</tr>
		
			
			<tr>
				<th>카테고리 이미지 정보</th>
				<td>${fn:replace(dto.getAlt_text(), newLine, br) }</td>
			</tr>
						<tr>
				<th>카테고리 등록일</th>
				<td>${dto.getUpload_date() }</td>
			</tr>
			
			<c:if test="${empty dto }">
				<tr>
					<td colspan="6" align="center">
						<h3>해당하는 게시글이 없습니다.</h3>
					</td>
				</tr>
			</c:if>
		</table>
		<br>
		
		<input class="btn" type="button" value="카테고리수정"
	        onclick="location.href='productCategoryModify.do?no=${dto.getCategory_No() }&currentPage=${pi}&status=${status}'">
	   &nbsp;&nbsp;
	   <input class="btn" type="button" value="카테고리삭제"
	        onclick="location.href='productCategoryDelete.do?no=${dto.getCategory_No() }&currentPage=${pi}&status=${status}'">
	   &nbsp;&nbsp;
	   <input class="btn" type="button" value="전체목록"
	        onclick="location.href='productCategoryList.do?currentPage=${pi}&status=&subtitle='">
	</div>
</body>
</html>