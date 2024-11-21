<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑몰 관리자 페이지</title>
<link rel="stylesheet" href="<%=request.getContextPath() %>/resources/index/css/index.css">
<script src="<%=request.getContextPath() %>/resources/index/js/index.js"></script>
</head>
<body>
<c:set var="contextPath" value="${ pageContext.servletContext.contextPath }" scope="application"/>


<div class="container">
    <div class="login-box">
        <h2>로그인</h2>
        <form>
            <div class="input-group">
                <label for="username">아이디</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="input-group">
                <label for="password">비밀번호</label>
                <input type="password" id="password" name="password" required>
            </div>
            <button type="submit">로그인</button>
            <div class="links">
            </div>
        </form>
    </div>
</div> 


</body>
</html>