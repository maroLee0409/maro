<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.sql.Date" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑몰 페이지</title>
<%

%>


</head>
<body>

<c:set var="contextPath" value="${ pageContext.servletContext.contextPath }" scope="application"/>

<script>
    window.onload = function() {
      location.href = "${contextPath}/main.go";
    }
</script>


</body>
</html>