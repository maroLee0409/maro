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
			<h3>유저 등록 페이지</h3>
		<hr width = "50%" color = "red">
		
		<br><br>
		
		<form method = "post" action = "${contextPath }/userInsert_ok.do">
			<table border = "1" width = "auto">
			<tr>
				<th>아이디</th>
				<td>
					<input type = "text" name = "user_id">
				</td>
			</tr>
			
			<tr>
				<th>비밀번호</th>
				<td>
					<input type = "password" name = "user_pwd">
				</td>
			</tr>
			
			<tr>
				<th>이름</th>
				<td>
					<input type = "text" name = "name">
				</td>
			</tr>
			
			<tr>
				<th>이메일</th>
				<td>
					<input type = "text" name = "email">
				</td>
			</tr>
			
			<tr>
				<td colspan = "2" align = "center">
					<input type = "submit" value = "등록">&nbsp;
					<input type = "reset" value = "다시">
				</td>
			</tr>
			</table>
		</form>
	</div>
</body>
</html>