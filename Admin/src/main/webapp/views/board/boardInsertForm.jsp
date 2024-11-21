<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 작성</title>
    <link rel="stylesheet" href="${contextPath}/resources/master.css">

    <!-- Froala Editor CDN -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/4.0.12/css/froala_editor.pkgd.min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/4.0.12/js/froala_editor.pkgd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/froala-editor/4.0.12/js/languages/ko.js"></script>

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
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

        .fr-box {
            min-height: 400px;
        }

    </style>
</head>
<body>
    <div class="content">
        <h2>게시글 작성</h2>
        <form action="boardInsertOk.do" method="post" enctype="multipart/form-data" onsubmit="return syncEditorContent()">
            <input type="hidden" name="userNo" value="${sessionScope.user.userNo}">
            <input type="hidden" name="userId" value="${sessionScope.user.userId}">

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

            <!-- Froala Editor (본문 입력) -->
            <div class="form-group">
                <label for="content">내용</label>
                <textarea id="content" name="content"></textarea>
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

            <!-- 제출 버튼 -->
            <div class="form-group">
                <button type="submit" class="btn btn-submit">게시글 등록</button>
            </div>
        </form>
    </div>
</body>

</html>
