<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>쇼핑몰 페이지</title>
<link rel="stylesheet" href="${contextPath }/resources/index/css/main.css">
<script src="${contextPath }/resources/index/js/main.js" defer></script>
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
</head>
<body>
<!-- 이벤트 배너 -->
<c:import url="common/eventTopBanner.jsp"/>

<%-- header 영역 --%>
<c:import url="common/userMenu.jsp"/>

<!-- 슬라이드 섹션 -->
<c:import url="common/slideSection.jsp"/>

<c:import url="common/fix_menubar.jsp"/>

<!-- Main 영역 -->

<!-- 서블릿에서 전달된 URL에 따라 동적으로 JSP 페이지를 포함 -->
<c:choose>
    <c:when test="${not empty param.url}">
        <c:import url="${param.url}" />
        
    </c:when>
    <c:when test="${not empty url}">
        <c:import url="${url}" />
       	<!-- Include the pagination JSP -->
       	<!-- pi객체가 존재한다면. List페이지 이므로 페이지네이션 import -->
        <c:if test="${not empty pi }"> 
		    <c:import url="/views/common/pagination.jsp"/>
        </c:if>
    </c:when>
    <c:otherwise>
		<%-- 이곳에 main 컨텐츠가 표시됩니다 --%>
		<c:import url="common/menubar.jsp"/>
		<c:import url="main/contents.jsp" />
    </c:otherwise>
</c:choose>



<c:import url="common/subbar.jsp"/>    
<%-- footer 영역 --%>
<c:import url="common/footer.jsp"/>

</body>
</html>
