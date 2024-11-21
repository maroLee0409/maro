<%@page import="com.global.board.model.BoardDTO"%>
<%@ page import="com.global.utils.StringUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.ArrayList,java.util.List ,com.global.board.model.*"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 카테고리</title>
<script type="text/javascript">
/**
 * 
 */
 
 // JavaScript 함수: 클릭한 행의 ID를 가져와서 상세 페이지로 이동
function goToDetailPage(event) {
    const target = event.currentTarget;
    const No = target.getAttribute('data-id');
    if (No) {
        /* window.location.href = '${contextPath}/detail.do?no=' + userNo + '&status='+${status}+'&currentPage='+${pi.currentPage}; */
        window.location.href = '${contextPath}/boardCategoryUpdateForm.do?no=' + No + '&status=' + '${status}' + '&currentPage=' + '${pi.currentPage}';
    }
}

// 모든 행에 클릭 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('table tr[data-id]');

    rows.forEach(row => {
        row.addEventListener('click', goToDetailPage);
    });
});
</script>

</head>
<body>

	<div class="title">
	  	<h1>전체 게시판</h1>
	</div>	

<div align="center">
    <div class="spacer"></div> <!-- 공백 추가 -->

	<table border="1">
	    <tr>
	        <td class="table_header button" colspan="9" align="right">
	            전체 카테고리 수 : ${count}
	        </td>
	    </tr>
	    <tr>
	        <th>No.</th> 
	        <th>카테고리 이름</th> 
	        <th>카테고리 설명</th>	        
	    </tr>
	    <c:choose>
	        <c:when test="${not empty list}">
	            <c:forEach items="${list}" var="category">
	            <tr class="trlist" data-id="${category.categoryNo}">
	                <td>${category.categoryNo}</td>
	                <td>${category.name}</td>
	                <td>${StringUtils.stripHtml(category.description)}</td> <!-- Use the custom Java method to strip HTML tags -->
	            </tr>
	            </c:forEach>
	        </c:when>
	        <c:otherwise>
	            <tr>
	                <td colspan="10" align="center">
	                    <h3>카테고리가 존재하지 않습니다.</h3>
	                </td>
	            </tr>
	        </c:otherwise>
	    </c:choose>  
	    <tr>
	    
	        <td class="table_bottom button" colspan="9" align="center">
	        	<!-- 로그인한 유저만 게시글 작성 버튼 보이기 -->
            	<c:if test="${not empty sessionScope.user and sessionScope.user.userType == 'ADMIN'}">
	            	<input type="button" class="btn" value="카테고리 등록" onclick="location.href='${contextPath}/boardCategoryInsertForm.do'">
	            </c:if>
	        </td>
	        
	    </tr>
	</table>
	
</div>

</body>
</html>