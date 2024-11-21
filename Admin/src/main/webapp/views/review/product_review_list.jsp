<%@ page import="com.global.board.model.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="java.util.ArrayList,java.util.List ,com.global.board.model.*"%>
<%@ page import="com.global.utils.StringUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 리스트</title>
<script type="text/javascript">

//JavaScript 함수: 클릭한 행의 ID와 userType을 가져와서 상세 페이지로 이동
function goToDetailPage(event) {
 const target = event.currentTarget;
 const No = target.getAttribute('data-id');
 const userType = target.getAttribute('data-user-type');
 const currentPage = ${pi.currentPage};  // pi.currentPage를 자바스크립트 변수로 설정
 const status = '${status}';
 
 if (No) {
     window.location.href = '${contextPath}/productReviewDetail.do?boardNo=' + No + '&userType=' + userType + '&status=' + status + '&currentPage=' + currentPage;
 }
}

//모든 행에 클릭 이벤트 리스너 추가
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
	    <h1>리뷰 게시판</h1>
	    <c:if test="${ not empty param.subtitle}">
	        <nav aria-label="breadcrumb">
	            <ol class="breadcrumb">
	                <li>홈</li> <!-- 홈으로 이동 -->
	                <li>게시판 관리</li> <!-- 게시판 관리 페이지로 이동 -->
	                <li class="active" aria-current="page">${ param.subtitle }</li> <!-- 현재 위치 -->
	            </ol>
	        </nav>
	    </c:if>
	</div>

    <div class="container">
        <!-- 사이드바 메뉴 -->
        <c:import url="board/import/categorySideBar.jsp" />

        <!-- 메인 콘텐츠 -->
        <div class="main-content">
            <!-- 검색 바 추가 -->
			<c:import url="board/import/boardFilter.jsp" />

            <!-- 게시판 테이블 -->
            <div class="table-container">
                <table>
				    <tr>
				        <td class="table_header info" colspan="9" align="right">전체 리뷰 수: ${count}</td>
				    </tr>
				    
				    <tr>
				        <th class="border-th">ReviewNo.</th>
				        <th class="border-th">UserID. <span class="subtitle">(userNo)</span></th>
				        <th class="border-th">Category <span class="subtitle">(categoryCode)</span></th>
				        <th class="border-th">제목</th>
				        <th class="border-th">내용</th>
				        <th class="border-th">작성일</th>
				        <th class="border-th">수정일</th>
				        <th class="border-th">조회수</th>
				        <th class="border-th">상태</th>
				    </tr>
				    
				    <c:choose>
					    <c:when test="${not empty list}">
					        <c:forEach items="${list}" var="review" varStatus="status">
					            <!-- 제목과 내용을 잘라서 변수에 저장 -->
								<c:set var="truncatedTitle" value="${StringUtils.truncateWithEllipsis(review.title, 20)}" />
								<c:set var="truncatedContent" value="${StringUtils.truncateWithEllipsis(review.content, 30)}" />
					
					            <!-- 각 행의 데이터를 출력 -->
					            <tr class="trlist" data-id="${review.boardNo}" data-user-type="${review.userType}">
					                <td class="board-td">${review.boardNo}</td>
					                <td class="board-td">
					                
					                    <c:choose>
					                        <c:when test="${review.userNo == null}">
					                            null
					                        </c:when>
					                        
					                        <c:otherwise>
					                            ${review.userId}
					                            <span class="subtitle">(${review.userNo})</span>
					                        </c:otherwise>
					                    </c:choose>
					                    
					                </td>
					                
					                <td class="board-td">${review.categoryName} <span class="subtitle">(${review.categoryNo})</span></td>
					                <td class="board-td">
					                    <!-- 명확하게 문자열로 출력 -->
					                    <c:out value="${truncatedTitle}" />
					                </td>
					                <td class="board-td">
					                    <!-- stripHtml 메서드 결과를 명확히 문자열로 처리 -->
					                    <c:out value="${truncatedContent}" />
					                </td>
					                <td class="board-td"><fmt:formatDate value="${review.createAt}" /></td>
					                <td class="board-td"><fmt:formatDate value="${review.updateAt}" /></td>
					                <td class="board-td">${review.views}</td>
					                <td class="board-td">
					                
					                    <c:choose>
					                        <c:when test="${review.isDeleted == 'N'}">
					                            <c:set var="status" value="정상" />
					                        </c:when>
					                        
					                        <c:otherwise>
					                            <c:set var="status" value="삭제됨" />
					                        </c:otherwise>
					                    </c:choose>
					                    
					                    <c:out value="${status}" />
					                </td>
					            </tr>
					        </c:forEach>
					    </c:when>
					</c:choose>
					
				    <tr>
				        <td class="table_bottom button" colspan="9" align="center">
				        
   				            <!-- 로그인한 유저만 게시글 작성 버튼 보이기 -->
				            <c:if test="${not empty sessionScope.user}">
				                <input type="button" class="btn" value="리뷰 작성" onclick="location.href='productReviewinsert.do'">
				            </c:if>
				        </td>
				    </tr>
				    
				</table>

            </div>
        </div>
        
        <!-- 사이드바 메뉴 -->
		<c:import url="board/import/categorySideBar.jsp" />
		
    </div>
</body>
</html>
