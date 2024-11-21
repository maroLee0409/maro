<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>게시글 상세보기</title>
<link rel="stylesheet" href="${contextPath}/resources/board/css/board.css">
<!-- Froala Editor CDN -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/4.0.12/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/4.0.12/js/froala_editor.pkgd.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/4.0.12/js/languages/ko.js"></script>

</head>
<body>
    <div class="content">
	    <form action="boardUpdateOk.do" method="post">	
	    	<input type="hidden" name="userNo" value="${sessionScope.userNo}">
	    	<input type="hidden" name="boardNo" value="${info.boardNo}">
	        <!-- 카테고리 이름 -->
	        <!-- 카테고리 선택 -->
	        <div class="form-group">
	            <select name="categoryNo" id="categoryNo" required>
	                <c:forEach var="category" items="${categoryList}">
	                    <option value="${category.categoryNo}">${category.name}</option>
	                </c:forEach>
	            </select>
	        </div> 
	
	        <!-- 제목 -->
	        <h2 class="content-title form-group">
	        	<input type="text" name="boardTitle" value="${info.title}" required="required">
	        </h2>
	
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
	
	        <!-- 본문 내용 -->
		    <!-- 내용 입력 (CKEditor 적용) -->
	        <div class="form-group">
	            <textarea id="content" name="content" required><c:out value="${info.content}" escapeXml="false"/></textarea>
				<script>
                new FroalaEditor('#content', {
                    language: 'ko',
                    height: 400,
                    imageUploadURL: '${contextPath}/boardfileUpload.do',  // 이미지 업로드 URL
                    fileUploadURL: '${contextPath}/boardfileUpload.do',   // 파일 업로드 URL
                    imageAllowedTypes: ['jpeg', 'jpg', 'png', 'gif'],
                    fileAllowedTypes: ['application/pdf', 'application/msword'],
                    pluginsEnabled: [
                        'align', 'charCounter', 'codeBeautifier', 'codeView', 'colors', 
                        'draggable', 'emoticons', 'entities', 'file', 'fontFamily', 
                        'fontSize', 'fullscreen', 'image', 'imageManager', 'inlineStyle', 
                        'lineBreaker', 'link', 'lists', 'paragraphFormat', 'paragraphStyle', 
                        'quickInsert', 'quote', 'table', 'url', 'video', 'wordPaste'
                    ],
                    imageUploadParams: {
                        'X-CSRF-TOKEN': '${_csrf.token}'   // 이미지 업로드 시 CSRF 토큰 추가
                    },
                    fileUploadParams: {
                        'X-CSRF-TOKEN': '${_csrf.token}'   // 파일 업로드 시 CSRF 토큰 추가
                    },
                    events: {
                        'image.uploaded': function (response) {
                            var fileInfo = JSON.parse(response);
                            var imageUrl = fileInfo.fileUrl;
                            console.log("이미지 업로드 성공: ", imageUrl);
                            // Froala가 자동으로 이미지를 삽입하므로 추가 처리 불필요
                        },
                        'file.uploaded': function (response) {
                            var fileInfo = JSON.parse(response);
                            addUploadedFile(fileInfo.fileUrl, fileInfo.fileName, fileInfo.fileSize, fileInfo.fileType, '');
                        },
                        'image.removed': function ($img) {
                            var imgSrc = $img.attr('src');
                            removeImageFromServer(imgSrc);
                        },
                        'file.unlink': function ($file) {
                            var fileSrc = $file.attr('src');
                            removeFileFromServer(fileSrc);
                        }
                    },
                    imageUploadError: function (error, response) {
                        console.log("이미지 업로드 에러 발생: ", error);
                        alert("이미지를 업로드하는 중 오류가 발생했습니다.");
                    },
                    fileUploadError: function (error, response) {
                        console.log("파일 업로드 에러 발생: ", error);
                        alert("파일을 업로드하는 중 오류가 발생했습니다.");
                    }
                });

                // 업로드된 파일 정보를 세션에 추가하는 함수
                function addUploadedFile(fileUrl, fileName, fileSize, fileType, description) {
                    var uploadedFiles = sessionStorage.getItem('uploadedFiles') ? JSON.parse(sessionStorage.getItem('uploadedFiles')) : [];

                    uploadedFiles.push({
                        fileUrl: fileUrl,
                        fileName: fileName,
                        fileSize: fileSize,
                        fileType: fileType,
                        description: description || '' // 설명이 없을 경우 빈 문자열로 처리
                    });

                    sessionStorage.setItem('uploadedFiles', JSON.stringify(uploadedFiles));
                }

                // 이미지 삭제 요청 함수
                function removeImageFromServer(imageUrl) {
                    fetch('${contextPath}/boardDeleteImage.do', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': '${_csrf.token}'
                        },
                        body: JSON.stringify({ imageUrl: imageUrl })
                    }).then(response => response.json())
                      .then(data => {
                          if (data.success) {
                              console.log('이미지가 성공적으로 삭제되었습니다: ', imageUrl);
                          } else {
                              console.error('이미지 삭제에 실패했습니다: ', imageUrl);
                          }
                      })
                      .catch(error => {
                          console.error('이미지 삭제 중 오류 발생: ', error);
                      });
                }

                // 파일 삭제 요청 함수
                function removeFileFromServer(fileUrl) {
                    fetch('${contextPath}/boardDeleteFile.do', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'X-CSRF-TOKEN': '${_csrf.token}'
                        },
                        body: JSON.stringify({ fileUrl: fileUrl })
                    }).then(response => response.json())
                      .then(data => {
                          if (data.success) {
                              console.log('파일이 성공적으로 삭제되었습니다: ', fileUrl);
                          } else {
                              console.error('파일 삭제에 실패했습니다: ', fileUrl);
                          }
                      })
                      .catch(error => {
                          console.error('파일 삭제 중 오류 발생: ', error);
                      });
                }
                </script>
	        </div>            
			
	        <!-- 뒤로가기 버튼 -->
	        <button type="button" onclick="location.href='boardList.do?status=${status}&currentPage=${currentPage}'" class="btn btn_space_tb">뒤로가기</button>
			<!-- 수정하기 버튼 (수정 권한 : 본인이 작성한 게시판 or session의 userType : admin 
				그러나 현재 admin 관련 로직이 미구현 상태 이므로 해당 로직은 추후에 구축 예정 by 두리
			-->
			<button type="submit" class="btn btn_space_tb">수정하기</button>
		</form>	
    </div>
</body>
</html>
