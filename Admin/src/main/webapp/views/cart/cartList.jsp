<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        xhr.send('userNo=${sessionScope.user.userNo}&user_no=' + No);
        
        // 페이지 이동
        window.location.href = '${contextPath}/cartItem_list.do?user_no=' + No + '&currentPage=' + currentPage ;
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
<style type="text/css">


</style>
</head>
<body>
	<div class="title">
		<h3>장바구니 전체리스트</h3>
	</div>
	<div class="container">
		<div class="main-content">
			<div class="table-container board-container">
		
				<table>
				
				<c:set var="list" value="${CartList }" />
					<tr >
						<th class="border-th board-th-boardNo">장바구니 No.</th>
						<th class="border-th board-th-boardNo">유저 No.</th>
						<th class="border-th board-th-boardNo">유저 아이디</th>
						<th class="border-th board-th-boardNo">유저 이름</th>
						<th class="border-th board-th-boardNo">유저 이메일</th>
						<th class="border-th board-th-createAt">장바구니 생성일</th>
						<th class="border-th board-th-status" > 구매하기 / 장바구니 삭제 </th>
					</tr>
					
					<c:if test="${!empty list }">
						<c:forEach  items="${list }" var="dto" >
							
							<tr class="trlist" data-id="${dto.user_no}"  >
								<td class="board-td">${dto.cart_no }</td>
							
								<td class="board-td">${dto.user_no }</td>
								<td class="board-td">${dto.user_no }</td>
								<td class="board-td">
									<fmt:formatDate value="${dto.created_at }"/>
								</td>
								<td class="table_bottom button">					
								<input type="button" class="btn" value="구매하기" onclick="if(confirm('해당 장바구니 상품을 구매하시겠습니까?')){
													location.href='order_list.do?user_no=${dto.user_no }'} else{return;}">&nbsp;						
								<input type="button" class="btn" value="삭제" onclick="if(confirm('정말로 게시글을 삭제하시겠습니까?')){
													location.href='cart_delete.do?no=${dto.cart_no}'} else{return;}">
								</td>
							</tr>
						</c:forEach>

					</c:if>
						<c:if test="${empty list }">
						<tr>
							<td colspan="5" align="center">
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