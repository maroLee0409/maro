
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
<!-- 스크롤 시 따라다니는 메뉴바 -->
<nav class="fixed-category-menu hidden">
    <div class="fixed-menu-container">
        <!-- 좌측 로고 -->
        <div class="fixed-menu-logo">
            <h1>LOGO</h1>
        </div>
        <ul class="fixed-menu-items">
            <li>
                <a href="#">
                    <img src="resources/index/img/category-menu/man_shirts 100x100.jpg" alt="MAN" class="menu-icon">
                    MAN
                </a>
            </li>
            <li>
                <a href="#">
                    <img src="resources/index/img/category-menu/woman_shirt 100x100.jpg" alt="WOMAN" class="menu-icon">
                    WOMAN
                </a>
            </li>
            <li>
                <a href="#">
                    <img src="resources/index/img/category-menu/digit_100x100.jpg" alt="DIGITAL" class="menu-icon">
                    DIGITAL
                </a>
            </li>
            <li>
                <a href="#">
                    <img src="resources/index/img/category-menu/event.jpg" alt="EVENT" class="menu-icon">
                    EVENT
                </a>
            </li>
            <li>
                <a href="#">
                    <img src="resources/index/img/category-menu/magagin.jpg" alt="MAGAZINE" class="menu-icon">
                    MAGAZINE
                </a>
            </li>
            <li>
                <a href="#">
                    <img src="resources/index/img/category-menu/stylish.jpg" alt="STYLIST" class="menu-icon">
                    STYLIST
                </a>
            </li>
        </ul>
        <!-- 우측 아이콘 메뉴 -->
        <div class="fixed-menu-icons">
            <a href="#"><i class="fas fa-user-plus"></i></a> <!-- 회원가입 -->
            <a href="#"><i class="fas fa-sign-in-alt"></i></a> <!-- 로그인 -->
            <a href="#"><i class="fas fa-comments"></i></a> <!-- 커뮤니티 -->
        </div>
    </div>
</nav>