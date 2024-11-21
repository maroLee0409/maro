<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시판 작성 폼</title>
<!-- 스타일링은 이미 준비된 CSS 파일을 사용 -->
<link rel="stylesheet" href="${contextPath }/resources/master.css">
<style type="text/css">
    body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
        margin: 0;
        padding: 0;
    }

    .content {
        max-width: 1250px;
        margin: 50px auto;
        padding: 20px;
        background-color: #ffffff;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    h2 {
        margin-bottom: 20px;
        color: #333;
        font-size: 28px;
        text-align: center;
    }

    #content {
        height: 400px;
        width: 100%;
    }

    .ck-editor__editable {
        min-height: 400px;
    }

    /* 이미지 미리보기 스타일 */
    .image-preview {
        margin-top: 15px;
        max-width: 100%;
        height: auto;
        display: none;
    }
</style>
<script type="text/javascript">

window.addEventListener('beforeunload', function (e) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'invalidateBoardSession.do', true);
    xhr.setRequestHeader('Content-Type', 'application/json');  // JSON 요청으로 설정
    
    // 서버로 전송할 데이터 (현재 세션 정보)
    var data = JSON.stringify({
        sessionId: '${session.id}'
    });

    // JSON 데이터를 서버로 전송
    xhr.send(data);
});

</script>

</head>
<body>
    <div class="content">
        <h2>게시글 작성</h2>
        <!-- 파일 업로드를 위한 enctype 추가 -->
        <form action="boardInsertOk.do" method="post" enctype="multipart/form-data" onsubmit="return syncEditorContent()">
            <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">

            <!-- 카테고리 선택 -->
            <div class="form-group">
                <label for="categoryNo">카테고리</label>
                <select name="categoryNo" id="categoryNo" required>
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category.categoryNo}">${category.name}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- 제목 입력 -->
            <div class="form-group">
                <label for="title">제목</label>
                <input type="text" id="title" name="title" required>
            </div>

            <!-- 내용 입력 (CKEditor 5 적용) -->
            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" name="content"></textarea>
                <script>
                    ClassicEditor
                        .create(document.querySelector('#content'), {
                        	language:'ko', // 한국어 설정
                            ckfinder: {
                                uploadUrl: '${contextPath}/boardfileUpload.do'
                            }
                        })
                        .catch(error => {
                            console.error(error);
                        });
                </script>
            </div>

            <!-- 파일 업로드 기능 (다중 파일 지원) -->
            <div class="form-group">
                <label for="files">파일 첨부</label>
                <!-- name을 배열 형태인 []로 설정하여 다중 파일 업로드를 지원 -->
                <input type="file" id="files" name="files[]" accept="image/*, application/pdf, application/msword" multiple>
                <!-- 미리보기 이미지 영역 (다중 파일일 경우 첫 번째 파일만 미리보기 지원) -->
                <img id="image-preview" class="image-preview" alt="이미지 미리보기">
            </div>

            <script>
                // 이미지 미리보기 기능
                function previewImage(event) {
                    const imagePreview = document.getElementById('image-preview');
                    const file = event.target.files[0];

                    if (file) {
                        const reader = new FileReader();
                        reader.onload = function(e) {
                            imagePreview.src = e.target.result;
                            imagePreview.style.display = 'block';
                        }
                        reader.readAsDataURL(file);
                    } else {
                        imagePreview.style.display = 'none';
                    }
                }
            </script>

            <!-- 제출 버튼 -->
            <div class="form-group">
                <button type="submit" class="btn btn-submit">게시글 등록</button>
            </div>
        </form>
    </div>
</body>
</html>
