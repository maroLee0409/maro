<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>배송 정보</title>
    <script type="text/javascript">
    function toggleInputFields() {
        var filterType = document.getElementById("filterType").value;
        var inputFieldContainer = document.getElementById("inputFields");

        if (filterType === "orderNoAndStatus" || filterType === "deliveryNoAndStatus") {
            inputFieldContainer.innerHTML = `
                <input type="text" id="firstFilterValue" onkeyup="filterTable()" placeholder="검색">
                <input type="text" id="secondFilterValue" onkeyup="filterTable()" placeholder="검색">
            `;
        } else {
            inputFieldContainer.innerHTML = `
                <input type="text" id="filterValue" onkeyup="filterTable()" placeholder="검색">
            `;
        }
    }

    function filterTable() {
        var filterType = document.getElementById("filterType").value;
        var table = document.getElementById("deliveryTable");
        var rows = table.getElementsByTagName("tr");
        var match = false;

        for (var i = 1; i < rows.length; i++) { // 첫 번째 행은 헤더이므로 1부터 시작
            var cells = rows[i].getElementsByTagName("td");

            if (filterType === "orderNoAndStatus") {
                var orderNoValue = document.getElementById("firstFilterValue").value.toLowerCase();
                var statusValue = document.getElementById("secondFilterValue").value.toLowerCase();
                match = cells[1].textContent.toLowerCase().includes(orderNoValue) &&
                        cells[3].textContent.toLowerCase().includes(statusValue);
            } else if (filterType === "deliveryNoAndStatus") {
                var deliveryNoValue = document.getElementById("firstFilterValue").value.toLowerCase();
                var statusValue = document.getElementById("secondFilterValue").value.toLowerCase();
                match = cells[0].textContent.toLowerCase().includes(deliveryNoValue) &&
                        cells[3].textContent.toLowerCase().includes(statusValue);
            } else {
                var filterValue = document.getElementById("filterValue").value.toLowerCase();
                if (filterType === "deliveryNo") {
                    match = cells[0].textContent.toLowerCase().includes(filterValue);
                } else if (filterType === "orderNo") {
                    match = cells[1].textContent.toLowerCase().includes(filterValue);
                } else if (filterType === "deliveryDate") {
                    match = cells[2].textContent.toLowerCase().includes(filterValue);
                } else if (filterType === "deliveryStatus") {
                    match = cells[3].textContent.toLowerCase().includes(filterValue);
                }
            }

            rows[i].style.display = match ? "" : "none"; // 일치 여부에 따라 표시/숨기기
        }
    }
    </script>
</head>
<body onload="toggleInputFields()">
    <div align="center">
        <h3>배송 정보</h3>
        
        <div>
            <label for="filterType">검색 기준:</label>
            <select id="filterType" onchange="toggleInputFields()">
                <option value="deliveryNo">배송 번호</option>
                <option value="orderNo">주문 번호</option>
                <option value="deliveryDate">배송일</option>
                <option value="deliveryStatus">배송 상태</option>
                <option value="orderNoAndStatus">주문 번호 + 배송 상태</option>
                <option value="deliveryNoAndStatus">배송 번호 + 배송 상태</option>
            </select>
            <div id="inputFields">
                <input type="text" id="filterValue" onkeyup="filterTable()" placeholder="검색">
            </div>
        </div>

        <table id="deliveryTable" border="1">
            <tr>
                <th>배송 번호</th>
                <th>주문 번호</th>
                <th>배송일</th>
                <th>배송 상태</th>
                <th>설정</th>
            </tr>

            <c:if test="${not empty List}">
                <c:forEach items="${List}" var="dto">
                    <tr>
                        <td>${dto.delivery_no}</td>
                        <td>${dto.order_no}</td>
                        <td>${dto.delivery_date}</td>
                        <td>
                            <c:choose>
                                <c:when test="${dto.delivery_status == 'PENDING'}">배송 대기</c:when>
                                <c:when test="${dto.delivery_status == 'SHIPPED'}">배송 중</c:when>
                                <c:when test="${dto.delivery_status == 'DELIVERED'}">배송 완료</c:when>
                                <c:when test="${dto.delivery_status == 'CANCELLED'}">배송 취소</c:when>
                            </c:choose>
                        </td>
                        <td>
                            <c:if test="${sessionScope.user.userType == 'ADMIN'}">
                                <input class="btn" type="button" value="배송상태설정" onclick="location.href='delivery_modify.do?no=${dto.order_no}'">
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            <c:if test="${empty List}">
                <tr>
                    <td colspan="5" align="center">
                        <h3>배송 목록이 없습니다.</h3>
                    </td>
                </tr>
            </c:if>
        </table>
    </div>
</body>
</html>
