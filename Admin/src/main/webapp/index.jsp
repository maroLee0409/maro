<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.global.admin.model.*" %>
<%@ page import="java.sql.Date" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑몰 관리자 페이지</title>
<%

//가데이터 생성	
//관리자 객체 생성
   AdminDTO admin = new AdminDTO();
admin.setUserNo(1);
admin.setUserId("admin");
admin.setPassword("1234");
admin.setName("관리자");
admin.setEmail("admin@example.com");
admin.setUserType("ADMIN");
admin.setIsDeleted("N");
admin.setCreatedAt(new Date(System.currentTimeMillis()));  // 현재 시간으로 설정
admin.setUpdatedAt(new Date(System.currentTimeMillis()));  // 현재 시간으로 설정

//추가적인 관리자 정보 설정
admin.setRoleCode(	"ADMIN001");
admin.setRoleName("최고 관리자");

//세션에 가데이터 저장
session.setAttribute("user", admin);  

 
// 가데이터 생성
// 고객 객체 생성
/*
CustomerDTO customer = new CustomerDTO();
customer.setUserNo(2);
customer.setUserId("Tester");
customer.setPassword("1234");
customer.setName("고객1");
customer.setEmail("tester@example.com");
customer.setUserType("CUSTOMER");
customer.setIsDeleted("N");
customer.setCreatedAt(new Date(System.currentTimeMillis()));  // 현재 시간으로 설정
customer.setUpdatedAt(new Date(System.currentTimeMillis()));  // 현재 시간으로 설정

// 추가적인 고객 정보 설정
customer.setAge(30);
customer.setJob("프로그래머");
customer.setLocation("서울");
customer.setMileage(1000);
customer.setLastLoginDate(new Date(System.currentTimeMillis()));  // 현재 시간으로 설정
customer.setTotalPurchaseAmount(50000);

// 세션에 가데이터 저장
session.setAttribute("user", customer); */

 
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