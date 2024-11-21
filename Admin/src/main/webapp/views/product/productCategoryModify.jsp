<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="${contextPath }/resources/product/css/product.css"
	rel="stylesheet" type="text/css" />
<title>Insert title here</title>
<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f8f9fa;
    }

    .content {
        width: 1000px;
        max-width: 1250px;
        margin: 50px auto;
        padding: 20px;
        background-color: #ffffff;
        border-radius: 8px;
        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    }

    h3 {
        margin-bottom: 20px;
        color: #333;
        font-size: 28px;
        text-align: center;
    }
</style>
</head>
<body>
    <div class="content">
        <c:set var="dto" value="${Modify }" />
        <h3>${dto.getName() } 카테고리 수정 페이지</h3>
        
        <form method="post" enctype="multipart/form-data" action="<%=request.getContextPath()%>/productCategoryModifyOk.do">
            <input type="hidden" name="category_no" value="${dto.getCategory_No() }">
            <div class="form-group">
                <label for="product_info">카테고리 번호</label>
                <input type="text" name="category_no" readonly value="${dto.getCategory_No() }">
            </div>
            <div class="form-group">
                <label for="product_info">카테고리 목록</label>
                <input type="text" name="name" value="${dto.getName() }">
            </div>
            <div class="form-group">
                <label for="product_info">카테고리 정보</label>
                <textarea rows="8" cols="22" name="description"></textarea>
            </div>
            <div class="form-group">
                <label for="product_info">카테고리 이미지</label>
                <input type="file" name="image_url">
            </div>
            <div class="form-group">
                <label for="product_info">카테고리 이미지 정보</label>
                <textarea rows="8" cols="22" name="alt_text"></textarea>
            </div>

            <table>
                <tr>
                    <td class="table_bottom button categoryInsertBtn" colspan="3">
                        <input class="btn" type="button" onclick="location.href='productCategoryList.do?status=${status}&currentPage=${currentPage}'" value="뒤로가기"/>
                        <input class="btn" type="submit" value="카테고리 수정">
                        <input class="btn" type="reset" value="초기화">
                    </td>
                </tr>
            </table>
        </form>
    </div>
</body>
</html>