<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
// JavaScript 함수: 클릭한 행의 ID와 userType을 가져와서 상세 페이지로 이동
function goToDetailPage(event) {
    const target = event.currentTarget;
    const No = target.getAttribute('data-id');
    const currentPage = ${pi.currentPage};  // pi.currentPage를 자바스크립트 변수로 설정

    if (No) {
        // 서버에 방문 기록 저장 요청
        const xhr = new XMLHttpRequest();
        xhr.open('POST', '${contextPath}/boardSaveVisit.do', true);
        xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
        xhr.send('userNo=${sessionScope.user.userNo}&no=' + No);
        
        // 페이지 이동
        window.location.href = '${contextPath}/productContent.do?no=' + No + '&currentPage=' + currentPage ;
    }
}


// 모든 행에 클릭 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('table tr[data-id]');
    rows.forEach(row => {
    	 if (event.target.closest('td.table_bottom.button')) {
             event.stopPropagation(); // 이벤트 전파를 막아 tr 클릭 이벤트가 발생하지 않도록 함
             return;
         }
        row.addEventListener('click', goToDetailPage);

    });
    
});
document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('table tr[data-id]');
    rows.forEach(row => {
        row.addEventListener('click', function(event) {
            // 클릭된 요소가 삭제 버튼인지 확인
            if (event.target.closest('td.table_bottom.button')) {
                event.stopPropagation(); // 이벤트 전파를 막아 tr 클릭 이벤트가 발생하지 않도록 함
                return;
            }
            goToDetailPage(event);
        });
    });
});
</script>
</head>
<body>
	<div class="title">
		<h3>장바구니 목록</h3>
	</div>
	<div class="container">
		<div class="main-content">
			<div class="table-container board-container">
		
		<c:set var="cartitem" value="${CartItemList }"/>
		<table>
		<tr>
				<th class="border-th board-th-boardNo" >순번</th> 
				<th class="border-th board-th-boardNo" >상품번호</th> 
				<th class="border-th board-th-createAt">상품명</th>
				<th class="border-th board-th-createAt">수량</th> 
				<th class="border-th board-th-createAt" >가격</th> 
				<th class="border-th board-th-status" >수정  /  삭제</th>
			</tr>
			<c:if test="${!empty cartitem}">
			<c:forEach var ="item" items="${ cartitem }">
				<tr class="trlist" data-id="${item.product_no}"  >
					<td class="board-td" >${ item.cartItem_no }</td>
					<td class="board-td" >${ item.product_no }</td>
					<td class="board-td" >${ item.product_name }</td>
					<td class="board-td" ><fmt:formatNumber value="${ item.quantity }"/>개 </td>
					<td class="board-td" ><fmt:formatNumber value="${ item.product_price }"/>원 </td>
					<td class="table_bottom button" >
						<input type="button" class="btn" value="수정" onclick="location.href='cartItem_modify.do?no=${item.cartItem_no}'">&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="button" class="btn" value="삭제" onclick="if(confirm('정말로 게시글을 삭제하시겠습니까?')){
											location.href='cartItem_delete.do?no=${item.cartItem_no}&user_no=${item.user_no}'} else{return;}">
					</td>
				</tr>
			</c:forEach>
			</c:if>
			<c:if test="${empty  cartitem }">
				<tr>
					<td colspan="7" align="center">
						<h3>장바구니가 비어있습니다.</h3>
					</td>
					</tr>
				</c:if>
		</table>	
			</div>
		</div>
	</div>
</body>
</html>