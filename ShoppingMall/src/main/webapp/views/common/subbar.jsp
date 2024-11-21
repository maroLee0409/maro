
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fm"%>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>쇼핑몰</title>
    <link rel="stylesheet" href="<%=request.getContextPath() %>/resources/index/css/main.css">
    <script src="<%=request.getContextPath() %>/resources/index/js/main.js" defer></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<!-- 스크롤 시 따라다니는 아이콘 메뉴 -->
<div class="floating-icons">
    <ul>
        <li>
            <a href="#">
                <i class="fas fa-shopping-cart"></i>
                <br>
                장바구니
            </a>
        </li>
        <li>
            <a href="#">
                <i class="fas fa-history"></i>
                <br>
                최근본상품
            </a>
        </li>
        <li>
            <div class="up-down">
                <i class="fas fa-angle-up"></i>
            </div>
        </li>
        <li>
            <div class="up-down">
                <i class="fas fa-angle-down"></i>
            </div>
        </li>
    </ul>
</div>