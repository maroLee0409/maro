<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align = "center">
		<hr width = "50%" color = "red">
			<h3>관리자 리스트 페이지</h3>
		<hr width = "50%" color = "red">
		
		<br><br>
		
		<form method = "post" action = "<%= request.getContextPath() %>/adminInsert_search.do">
			<select name = "field">
	        	<option value = "name">관리자 이름</option>
	        	<option value = "name">직책 이름</option>
			</select>
			
			<input type = "text" name = "keyword">&nbsp;
	   	 	<input type = "submit" value = "검색">
	   	 </form>
	   	 
	   	 <br>
			
		 <table border = "1" width = "auto">
		 	<tr>
		 		<th>관리자 이름</th> <th>직책 이름</th> <th>이메일</th> 
		 	</tr>
		 	
		 	<c:set var = "list" value = "${List }"/>
		 	
		 	<c:if test = "${!empty list }">
		 		<c:forEach items = "${list }" var = "dto">
		 			<tr>
		 				<td>${dto.getName() }</td>
		 				<td>${dto.getRoleName() }</td>
		 				<td>${dto.getEmail() }</td>
		 				<td><input type = "button" value = "상세내역" onclick = "location.href='adminInsert_content.do'"></td>
		 			</tr>
		 		</c:forEach>
		 	</c:if>
		 	
		 	<c:if test = "${empty list }">
		 		<tr>
		 			<td colspan = "5" align = "center">
		 				<h3>전체 관리자 리스트가 없습니다.</h3>
		 			</td>
		 		</tr>
		 	</c:if>
		 </table>
		 
		 <br>
		 
		 <!-- 로그인한 유저만 게시글 작성 버튼 보이기 -->
          <c:if test="${not empty sessionScope.user}">
		 	<input type = "button" value = "등록" onclick = "location.href='adminInsert_Form.do'">
		  </c:if>
	</div>
</body>
</html>