<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- 스타일링은 이미 준비된 CSS 파일을 사용 -->
<link rel="stylesheet" href="${contextPath }/resources/master.css">
<%-- <link href="${contextPath }/resources/common/css/insertTableForm.css" rel="stylesheet" type="text/css" /> --%>
<!-- CKEditor 스크립트 추가 -->
<script src="https://cdn.ckeditor.com/4.16.2/standard/ckeditor.js"></script>
<script type="text/javascript">
	function goToDelete(){
		const categoryNo = '${ category.categoryNo }';
		if(confirm("정말로 삭제 하시겠습니까? 삭제된 데이터는 복구할 수 없습니다.")){
			location.href="boardCategoryDeleteOk.do?categoryNo=" + categoryNo;
		}
	}

</script>
</head>
<body>

    <div class="content">
        <h2>카테고리 수정</h2>
        <form action="boardCategoryUpdateOk.do" method="post">
            <input type="hidden" name="userNo" value="${sessionScope.userNo}">
            <input type="hidden" name="categoryNo" value="${ category.categoryNo }">

            <!-- 카테고리 No. -->
            <div class="form-group">
                <label for="title">Category_No</label>
                ${ category.categoryNo }
            </div>
            <!-- 제목 입력 -->
            <div class="form-group">
                <label for="title">카테고리 제목</label>
                <input type="text" id="title" name="name" value="${ category.name }"required>
            </div>

			<!-- 삭제 여부 -->
			<div class="form-group">
			    <label for="isDeleted">상태</label>
			    <select id="isDeleted" name="isDeleted" class="form-control">
			        <option value="N" ${category.isDeleted == 'N' ? 'selected' : ''}>활성</option>
			        <option value="Y" ${category.isDeleted == 'Y' ? 'selected' : ''}>삭제됨</option>
			    </select>
			</div>

            <!-- 제출 and 삭제 버튼 -->
            <div class="form-group">
                <button type="submit" class="btn btn-submit">카테고리 수정</button>
                <c:if test="${category.isDeleted == 'Y' }">
                	<button type="button" class="btn btn-submit" onclick="goToDelete()">카테고리 완전 삭제</button>
                </c:if>
            </div>
        </form>
    </div>

</body>
</html>