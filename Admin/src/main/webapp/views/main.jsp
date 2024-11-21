<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${contextPath }/resources/master.css" rel ="stylesheet" type="text/css"/>
<title>관리지 페이지</title>
<script type="text/javascript">
/* window.addEventListener('load', adjustMainContentHeight);
window.addEventListener('resize', adjustMainContentHeight);

function adjustMainContentHeight() {
    var boardContainer = document.querySelector('.board-container');
    var mainContent = document.querySelector('.main-content');

    if (boardContainer && mainContent) {
        // mainContent의 높이를 boardContainer 높이의 90%로 설정
        boardContainer.style.height = (mainContent.offsetHeight - 1000) + 'px';
    }
} */


</script>

</head>
<body>
<%-- header 영역 --%>
<c:import url="common/menubar.jsp"/>

    <!-- Main 영역 -->
    <div class="main-content"> <!-- 메인 콘텐츠 영역을 감싸는 div 추가 -->
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
			<c:import url="main/dashboard.jsp" />
	    </c:otherwise>
	</c:choose>
    </div> <!-- 메인 콘텐츠 영역 끝 -->
    
<%-- footer 영역 --%>
<c:import url="common/footer.jsp"/>

</body>
</html>
