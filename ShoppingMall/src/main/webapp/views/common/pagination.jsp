<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link href="${contextPath }/resources/common/css/pagination.css" rel="stylesheet" type="text/css"/>
<!-- pagination.jsp -->
<div class="pagination" align="center">
    <%-- 이전 버튼 --%>
    <c:if test="${pi.currentPage > 1}">
        <a
            href="${contextPath}/${address}?currentPage=${pi.currentPage - 1}&status=${status}&subtitle=${subtitle}"
            class="pagination-button">이전</a>
    </c:if>

    <%-- 페이지 번호 버튼 --%>
    <c:forEach var="pageNum" begin="${pi.startPage}" end="${pi.endPage}">
        <c:choose>
            <c:when test="${pageNum == pi.currentPage}">
                <span class="pagination-button current">${pageNum}</span>
            </c:when>
            <c:otherwise>
                <a
                    href="${contextPath}/${address}?currentPage=${pageNum}&status=${status}&subtitle=${subtitle}"
                    class="pagination-button">${pageNum}</a>
            </c:otherwise>
        </c:choose>
    </c:forEach>

    <%-- 다음 버튼 --%>
    <c:if test="${pi.currentPage < pi.maxPage}">
        <a
            href="${contextPath}/${address}?currentPage=${pi.currentPage + 1}&status=${status}&subtitle=${subtitle}"
            class="pagination-button">다음</a>
    </c:if>
</div>
