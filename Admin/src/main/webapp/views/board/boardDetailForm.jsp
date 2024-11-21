<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<link rel="stylesheet" href="${contextPath}/resources/board/css/board.css">
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
//게시글 삭제
function deleteBoard(boardNo) {
    if (confirm("정말 이 게시글을 삭제하시겠습니까?")) {
        location.href = 'boardDeleteOk.do?boardNo=' + boardNo + '&currentPage=${currentPage}';
    }
}

//게시글 삭제 취소
function restoreBoard(boardNo) {
    if (confirm("정말 이 게시글의 삭제를 취소하시겠습니까?")) {
        location.href = 'boardRestoreOk.do?boardNo=' + boardNo + '&currentPage=${currentPage}';
    }
}

//게시글 완전 삭제
function realDeleteBoard(boardNo) {
    if (confirm("정말 이 게시글을 완전히 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.")) {
        location.href = 'boardRealDeleteOk.do?boardNo=' + boardNo + '&currentPage=${currentPage}';
    }
}
</script>

<style>
    /* 댓글 및 대댓글 스타일링 */
    .comment {
        border: 1px solid #e1e1e1;
        padding: 10px;
        margin-bottom: 10px;
        border-radius: 4px;
        background-color: #f9f9f9;
    }

    .comment-info {
        font-size: 14px;
        color: #555;
        margin-bottom: 5px;
    }

    .comment-content {
        font-size: 16px;
        color: #333;
        margin-bottom: 10px;
    }

    .reply-link {
        display: inline-block;
        margin-top: 5px;
        font-size: 14px;
        color: #007bff;
        cursor: pointer;
    }

    .reply-link:hover {
        text-decoration: underline;
    }

    .reply-form, .edit-form {
        margin-top: 10px;
        margin-left: 20px;
        display: none;
        background-color: #f1f1f1;
        padding: 10px;
        border-radius: 4px;
    }

    .reply-form textarea, .edit-form textarea {
        width: 100%;
        height: 100px;
        border-radius: 4px;
        border: 1px solid #ced4da;
        padding: 8px;
        font-size: 14px;
        box-sizing: border-box;
    }

    /* 대댓글 구분 스타일 */
    .comment[data-node-level="2"] {
        margin-left: 40px;
        background-color: #f0f8ff;
    }

    .comment[data-node-level="3"] {
        margin-left: 60px;
        background-color: #e6f2ff;
    }

    .comment[data-node-level="4"] {
        margin-left: 80px;
        background-color: #cce0ff;
    }

    /* 댓글 액션 버튼 스타일 */
    .comment-actions {
        margin-top: 5px;
    }

    .comment-actions a {
        margin-right: 10px;
        font-size: 14px;
        color: #007bff;
        cursor: pointer;
    }

    .comment-actions a:hover {
        text-decoration: underline;
    }

    /* 파일 다운로드 버튼 스타일 */
    .file-download {
        margin: 20px 0;
        text-align: left;
    }

    .file-download a {
        padding: 10px 20px;
        color: blue;
        border-radius: 5px;
        display: inline-block;
    }

    .file-download a:hover {
        background-color: #0056b3;
    }

</style>

</head>
<body>
    <div class="content">
        <!-- 카테고리 이름 -->
        <div class="category-label">${info.categoryName}</div>

        <!-- 게시글 번호 -->
        <div class="board-no">게시글 번호: ${info.boardNo}</div>

        <!-- 제목 -->
        <h2 class="content-title">${info.title}</h2>

        <!-- 작성자 및 작성/수정일 정보 -->
        <div class="content-info">
            <span>작성자: ${info.userId} (${info.userName})</span>
            <c:choose>
                <c:when test="${not empty info.updateAt}">
                    <span>수정일: <fmt:formatDate value="${info.updateAt}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                </c:when>
                <c:otherwise>
                    <span>작성일: <fmt:formatDate value="${info.createAt}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                </c:otherwise>
            </c:choose>
        </div>

        <!-- 조회수 -->
        <div class="content-views">조회수: ${info.views}</div>

        <!-- 본문 내용 -->
        <div class="content-body">
            <c:out value="${info.content}" escapeXml="false"/>
        </div>

        <!-- 첨부된 파일 처리 -->
        <c:if test="${not empty files}">
            <div class="file-download">
                <c:forEach var="file" items="${files}">
                    <c:choose>
                        <c:when test="${file.fileType.startsWith('image/')}">
                            <a href="${contextPath}/boardFileDownload.do?fileUrl=${file.fileUrl}" >${file.fileName} 다운로드</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${contextPath}/boardFileDownload.do?fileUrl=${file.fileUrl}">${file.fileName} 다운로드</a>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
        </c:if>
		
        <!-- 뒤로가기 버튼 -->
        <button type="button" onclick="location.href='boardList.do?status=${status}&currentPage=${currentPage}'" class="btn btn_space_tb">뒤로가기</button>
        
        <!-- 수정하기 및 삭제하기 버튼: 작성자와 관리자만 가능 -->
        <c:if test="${sessionScope.user.userType == 'ADMIN' || sessionScope.user.userNo == info.userNo}">
            <button type="button" onclick="location.href='boardUpdateForm.do?no=${info.boardNo}&userType=${info.userType}&status=${status}&currentPage=${currentPage}'" class="btn btn_space_tb">수정하기</button>
            
            <!-- is_deleted 상태에 따라 다른 버튼 표시 -->
            <c:choose>
                <c:when test="${info.isDeleted == 'Y'}">
                    <!-- 삭제된 게시글의 경우: 삭제 취소 또는 완전 삭제 버튼 -->
                    <button type="button" onclick="restoreBoard(${info.boardNo})" class="btn btn_space_tb">삭제 취소</button>
                    <button type="button" onclick="realDeleteBoard(${info.boardNo})" class="btn btn_space_tb">완전 삭제하기</button>
                </c:when>
                <c:otherwise>
                    <!-- 삭제되지 않은 게시글의 경우: 삭제 버튼 -->
                    <button type="button" onclick="deleteBoard(${info.boardNo})" class="btn btn_space_tb">삭제하기</button>
                </c:otherwise>
            </c:choose>
        </c:if>

        <!-- 댓글 입력 폼 -->
        <c:import url="board/import/boardCommentInsertForm.jsp"/>

        <!-- 기존 댓글 표시 -->
        <c:import url="board/import/boardCommentList.jsp"/>
    </div>
    
    <c:import url="board/boardList_detail.jsp"/>
    
</body>
</html>
