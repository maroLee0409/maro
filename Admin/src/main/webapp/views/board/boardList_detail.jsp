<%@page import="com.global.user.model.UsersDTO"%>
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
// JavaScript 함수: 클릭한 행의 ID와 userType을 가져와서 상세 페이지로 이동
function goToDetailPage(event) {
    const target = event.currentTarget;
    const No = target.getAttribute('data-id');
    const userType = target.getAttribute('data-user-type');
    const currentPage = ${pi.currentPage};  // pi.currentPage를 자바스크립트 변수로 설정
    const status = '${status}';

    if (No) {
        // 서버에 방문 기록 저장 요청
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '${contextPath}/boardSaveVisit.do', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        
        xhr.send('userNo=${sessionScope.user.userNo}&boardNo=' + No);
        
        // 페이지 이동
        window.location.href = '${contextPath}/boardDetailForm.do?boardNo=' + No + '&userType=' + userType + '&status=' + status + '&currentPage=' + currentPage;
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

    <div class="container">

        <!-- 메인 콘텐츠 -->
        <div class="main-content">

            <!-- 게시판 테이블 -->
            <div class="table-container board-container">
                <table>
				    <tr>
				        <td class="table_header info" colspan="9" align="right">전체 게시글 수: ${count}</td>
				    </tr>
				    <tr>
				        <th class="border-th board-th-boardNo">BoardNo.</th>
				        <th class="border-th">Category <span class="subtitle">(categoryCode)</span></th>
				        <th class="border-th">UserID. <span class="subtitle">(userNo)</span></th>
				        <th class="border-th board-th-title">제목</th>
				        <!-- <th class="border-th">내용</th> -->
				        <th class="border-th board-th-createAt">작성일</th>
				        <th class="border-th board-th-updateAt">수정일</th>
				        <th class="border-th board-th-views">조회수</th>
				        <th class="border-th board-th-status">상태</th>
				    </tr>
				    <c:choose>
					    <c:when test="${not empty list}">
					        <c:forEach items="${list}" var="board" varStatus="status">
					            <!-- 제목과 내용을 잘라서 변수에 저장 -->
 
								<c:set var="truncatedTitle" value="${StringUtils.truncateWithEllipsis(board.title, 17)}" />
								<c:set var="truncatedContent" value="${StringUtils.truncateWithEllipsis(board.content, 14)}" />
					
					            <!-- 각 행의 데이터를 출력 -->
					            <tr class="trlist" data-id="${board.boardNo}" data-user-type="${board.userType}">
					                <td class="board-td">${board.boardNo}</td>
					                <td class="board-td">${board.categoryName} <span class="subtitle">(${board.categoryNo})</span></td>
					                <td class="board-td">
					                    <c:choose>
					                        <c:when test="${board.userNo == null}">
					                            null
					                        </c:when>
					                        <c:otherwise>
					                            ${board.userId}
					                            <span class="subtitle">(${board.userNo})</span>
					                        </c:otherwise>
					                    </c:choose>
					                </td>
									<td class="board-td">
									    <!-- 제목과 함께 댓글 수 표시 -->
									    <c:out value="${truncatedTitle}" />
									    <c:if test="${board.commentCount > 0}">
									        <span class="commentCount">[${board.commentCount}]</span>
									    </c:if>
									</td>
<%-- 					                <td class="board-td">
					                    <!-- stripHtml 메서드 결과를 명확히 문자열로 처리 -->
					                    <c:out value="${truncatedContent}" />
					                </td> --%>
					                <td class="board-td"><fmt:formatDate value="${board.createAt}" /></td>
					                <td class="board-td"><fmt:formatDate value="${board.updateAt}" /></td>
					                <td class="board-td">${board.views}</td>
					                <td class="board-td">
					                    <c:choose>
					                        <c:when test="${board.isDeleted == 'N'}">
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
				                <input type="button" class="btn" value="게시글 작성" onclick="location.href='boardInsertForm.do'">
				            </c:if>
				        </td>
				    </tr>
				</table>

            </div>
        </div>

</div>

</body>
</html>
