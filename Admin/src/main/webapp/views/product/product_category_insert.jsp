<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>카테고리 등록</title>
<link href="${contextPath }/resources/product/css/categoryInsert.css" rel ="stylesheet" type="text/css"/>
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
    <h3>카테고리 등록</h3>

    <form action="${ contextPath }/productCategoryInsert.do" method="post" enctype="multipart/form-data" onsubmit="return validatePasswords()">
        <div class="form-group">
            <label for="category_no">카테고리 번호</label>
                <input type="text" name="category_no" required>
        </div>    
        <div class="form-group">  
        	<label for="name">카테고리 이름</label>
                <input type="text" name="name" required>
        </div>        
        <div class="form-group">
        	<label for="description">카테고리 정보</label>
                <textarea rows="8" cols="22" name="description"></textarea>
        </div>        
        <div class="form-group">    
        	<label for="image_url">카테고리 이미지</label>
				<input type="file" name="image_url">
		</div>		
		<div class="form-group">		
			<label for="alt_text">카테고리 이미지 정보</label>
				<textarea id="content" rows="8" cols="22" name="alt_text"></textarea>
		</div>
             <table>
             <tr>
                <td class="table_bottom button categoryInsertBtn" colspan="2">
                    <input class="btn" type="submit" value="카테고리 등록">
                    <input class="btn" type="reset" value="초기화">
                </td>
            </tr>
         </table>
     </form>
     </div>

</body>
</html>