<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fm" %>    

        <div class="comment-section">
            <h3>댓글 작성</h3>
            <form id="commentForm">
                <input type="hidden" name="boardNo" value="${info.boardNo}">
                <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
                <input type="hidden" name="status" value="${status}">
                <input type="hidden" name="currentPage" value="${currentPage}">
                <div class="form-group">
                    <label for="commentContent">내용:</label>
                    <textarea id="commentContent" name="content" rows="4" class="input-field" required></textarea>
                </div>
                <button type="submit" class="btn btn_primary">댓글 작성</button>
            </form>
        </div>

        
<script>
document.addEventListener("DOMContentLoaded", function() {
    // 일반 댓글 작성 처리
    document.getElementById("commentForm").addEventListener("submit", function(event) {
        event.preventDefault();
        
        var content = document.getElementById('commentContent').value;
        if (content.trim() === "") {
            alert("내용을 입력하세요.");
            return;
        }

        var formData = {
            boardNo: document.querySelector('input[name="boardNo"]').value || null,
            userNo: document.querySelector('input[name="userNo"]').value || null || 4, // null일 경우 기본값 4
            content: content,
            parentReplyNo: null // 일반 댓글이므로 parentReplyNo는 null
        };

        var xhr = new XMLHttpRequest();
        xhr.open("POST", "boardReplyInsert.do", true);
        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

        xhr.onload = function() {
            if (xhr.status >= 200 && xhr.status < 300) {
                var response = JSON.parse(xhr.responseText);
                if (response.success) {
                    location.reload(); // 댓글이 성공적으로 추가된 후 페이지를 새로고침
                } else {
                    alert('댓글 등록에 실패했습니다.');
                }
            } else {
                alert('댓글 등록에 실패했습니다.');
            }
        };

        xhr.send(JSON.stringify(formData));
    });
});
</script>