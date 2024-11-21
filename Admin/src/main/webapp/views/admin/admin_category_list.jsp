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
			<h3>관리자 직책 리스트 페이지</h3>
		<hr width = "50%" color = "red">
		
		<br><br>
		
		<form method = "post" action = "<%= request.getContextPath() %>/adminCategory_search.do">
			<select name = "field">
				<option name = "role_code">직책 코드</option>
				<option name = "role_name">직책 이름</option>
			</select>
			
			<input type = "text" name = "keyword">&nbsp;
	   	 	<input type = "submit" value = "검색">
	   	 </form>
	   	 
	   	 <br>
		 	
		 <table border = "1" width = "auto">
		 	<tr>
		 		<th>직책 코드</th> <th>직책 이름</th> <th>수정</th> <th>삭제</th>
		 	</tr>
		 	
		 	<c:set var = "list" value = "${List }"/>
			 	<c:if test = "${!empty List }">
				 	<c:forEach var = "adminRole" items = "${list }">
			 			<tr>
			 				<td>${adminRole.getRoleCode() }</td>
			 				<td>${adminRole.getRoleName() }</td>
			 				<td><input type = "button" value = "수정" onclick = "location.href='adminCategory_modify.do'"></td>
			 				<td><input type = "button" value = "삭제" onclick = "location.href='adminCategory_delete.do'"></td>
			 			</tr>
				 	</c:forEach>
			 	</c:if>
			 	
		 		<c:if test = "${empty list }">
		 			<tr>
		 				<td colspan = "4" align = "center">
		 					<h3>전체 관리자 직책 리스트가 없습니다.</h3>
		 				</td>
		 			</tr>
		 		</c:if>
		 </table>
		 
		 <br>
		 
		 <!-- 로그인한 관리자만 등록 버튼 보이기 -->
         <c:if test="${not empty sessionScope.user}">
		 	<input type = "button" value = "등록" onclick = "location.href='adminCategory_Form.do'">
		 </c:if>
	</div>
</body>
</html>