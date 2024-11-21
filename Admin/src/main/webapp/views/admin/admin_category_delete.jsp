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
			<h3>관리자 직책 삭제 페이지</h3>
		<hr width = "50%" color = "red">
		
		<br><br>
		
		<form method = "post" action="<%= request.getContextPath() %>/delete_ok.do">
	   	  <input type = "hidden" name = "mem_no" value = "${No }">
	      <table border = "1" width = "auto">
	         <tr>
	            <td colspan = "1" align = "center">
	               <input type = "submit" value = "삭제">&nbsp;
	            </td>
	         </tr>
	      </table>
	   </form>
	</div>
</body>
</html>