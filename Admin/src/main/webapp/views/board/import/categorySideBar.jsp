<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fm" %>
<div class="sidebar">
    <h3>게시판 카테고리</h3>
    <ul>
        <li><a href="boardList.do?subtitle=${param.subtitle}">전체 게시글</a></li>
        <c:forEach var="category" items="${categoryList}">
            <li><a href="boardCategoryFilter.do?categoryNo=${category.categoryNo}&subtitle=${category.name}&currentPage=${pi.currentPage}">${category.name}</a></li>
        </c:forEach>
    </ul>
</div>