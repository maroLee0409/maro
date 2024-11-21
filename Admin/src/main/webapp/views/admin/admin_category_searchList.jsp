<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div align = "center">
		<hr width = "50%" color = "red">
			<h3>관리자 직책 검색 목록 페이지</h3>
		<hr width = "50%" color = "red">
		
		<br><br>
		
		<form method = "post" action = "<%= request.getContextPath() %>/adminCategory_search.do">
	      <select name="field">
	         <option value = "rolecode">직책 코드</option>
	         <option value = "rolename">직책 이름</option>
	      </select>
	      
	      <input type = "text" name = "keyword">&nbsp;
	   	  <input type = "submit" value = "검색">
	   </form>
	   
	   <br>
	   
	   <table border = "1" width = "auto">
	      <tr>
	         <th>직책 코드</th> <th>직책 이름</th>
	      </tr>
	      
	      <c:set var = "list" value = "${Search }" />
	      
	      <c:if test = "${!empty list }">
	         <c:forEach items = "${list }" var = "dto">
	            <tr>
	               <td> ${dto.getRoleCode() } </td>
	               <td> ${dto.getRoleName() } </td>
	            </tr>
	         </c:forEach>
	      </c:if>
	      
	      <c:if test = "${empty list }">
	         <tr>
	            <td colspan = "4" align = "center">
	               <h3>검색 직책 리스트가 없습니다.</h3>
	            </td>
	         </tr>
	      </c:if>
	   </table>
	   
	   <br>
	   
	   <input type = "button" value = "회원목록" onclick = "location.href='adminCategory_list.do'">
	</div>
</body>
</html>