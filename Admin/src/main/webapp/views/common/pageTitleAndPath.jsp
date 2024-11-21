<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="title">
    <h1>${location.title}</h1> <!-- 동적 제목 출력 -->
    ${location.displayBreadcrumb()} <!-- BreadCrumb 경로 출력 -->
</div>