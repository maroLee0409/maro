<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>에러 발생</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f4f4;
        margin: 0;
        padding: 0;
    }
    .container {
        max-width: 600px;
        margin: 50px auto;
        background-color: #fff;
        padding: 20px;
        box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
    }
    h1 {
        color: #d9534f;
    }
    p {
        margin-top: 20px;
    }
    .btn {
        display: inline-block;
        padding: 10px 20px;
        background-color: #007bff;
        color: #fff;
        text-decoration: none;
        border-radius: 4px;
        margin-top: 20px;
    }
</style>
</head>
<body>
    <div class="container">
        <h1>에러 발생</h1>
        <p>요청을 처리하는 도중 문제가 발생하였습니다. 다음 정보를 확인해 주세요:</p>
        <p><strong>에러 메시지:</strong> ${errorMsg}</p>
        <a href="${contextPath}/index.jsp" class="btn">홈으로 돌아가기</a>
    </div>
</body>
</html>
