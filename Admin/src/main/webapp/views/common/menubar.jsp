
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fm" %>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">    

<link href="${contextPath }/resources/master.css" rel="stylesheet" >
<link href="${contextPath }/resources/common/css/pwd.css" rel="stylesheet" type="text/css"/>
<link href="${contextPath }/resources/common/css/table.css" rel="stylesheet" type="text/css"/>
<link href="${contextPath }/resources/common/css/btn.css" rel="stylesheet">    
<link href="${contextPath }/resources/common/css/form.css" rel="stylesheet">
<link href="${contextPath }/resources/common/css/components.css" rel="stylesheet">
<link href="${contextPath }/resources/common/css/search.css" rel="stylesheet">
<link href="${contextPath }/resources/common/css/sidebar.css" rel="stylesheet">
<link href="${contextPath }/resources/common/css/filter.css" rel="stylesheet">
<link href="${contextPath }/resources/board/css/board.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<!-- CKEditor 5 스크립트 추가 -->
<script src="https://cdn.ckeditor.com/ckeditor5/36.0.1/classic/ckeditor.js"></script>


<!-- 내비게이션 바 -->
<nav class="navbar">
    <div class="container">
        <a href="main.go" class="logo">관리자 대시보드</a>
        <ul class="nav-links">
            <li><a href="main.go">홈</a></li>
            
            <!-- 사용자 관리 -->
            <li class="dropdown">사용자 관리
                <ul class="dropdown-menu">
                    <li><a href="userInsert_list.do">사용자 목록</a></li>
                    <li><a href="user_add.html">사용자 추가</a></li>
                    <li><a href="user_stats.html">사용자 통계</a></li>
                </ul>
            </li>
            
            <!-- 상품 관리 -->
            <li class="dropdown">상품 관리
                <ul class="dropdown-menu">
                    <li><a href="productList.do">상품 목록</a></li>
                    <li><a href="productInsert.do">상품 추가</a></li>
                    <li><a href="productCategoryList.do">카테고리 관리</a></li>
                    <li><a href="product_stats.html">상품 통계</a></li>
                </ul>
            </li>
            
             <!-- 장바구니 관리 -->
            <li class="dropdown">장바구니 관리
                <ul class="dropdown-menu">
                    <li><a href="cart_list.do">장바구니 목록</a></li>
                </ul>
            </li>
            

            <!-- 주문 관리 -->
            <li class="dropdown">주문 관리
                <ul class="dropdown-menu">
                    <li><a href="order_list.do">주문 목록</a></li>
                    <li><a href="order_stats.html">주문 통계</a></li>
                    <li><a href="delivery_list.do">배송 상태 관리</a></li>
                </ul>
            </li>

            <!-- 게시판 관리 -->
            <li class="dropdown">게시판 관리
                <ul class="dropdown-menu">
                    <li><a href="boardList.do?subtitle=게시글 목록">게시글 목록</a></li>
                    <li><a href="boardCategoryList.do">카테고리 관리</a></li>
                    <li><a href="board_stats.html">게시글 통계</a></li>
                </ul>
            </li>
            
            <!-- 리뷰 관리 -->
            <li class="dropdown">리뷰 관리
                <ul class="dropdown-menu">
                    <li><a href="productReviewList.do">리뷰 목록</a></li>
                    <li><a href="review_stats.html">리뷰 통계</a></li>
                    <li><a href="myPage.do">마이페이지</a></li>
                </ul>
            </li>
            
            <!-- 관리자 관리 -->
            <li class="dropdown">관리자 관리
                <ul class="dropdown-menu">
                    <li><a href="adminInsert_list.do">관리자 목록</a></li>
                    <li><a href="adminCategory_list.do">역할 관리</a></li>
                </ul>
            </li>

            <!-- 이벤트 관리 -->
            <li class="dropdown">이벤트 관리
                <ul class="dropdown-menu">
                    <li><a href="eventList.do?eventType=EVENT">일반 이벤트 목록</a></li>
                    <li><a href="eventList.do?eventType=BANNER">베너 이벤트 목록</a></li>
                    <li><a href="eventList.do?eventType=COUPON">쿠폰 이벤트 목록</a></li>
                    <li><a href="email_log.html">이메일 발송 기록</a></li>
                </ul>
            </li>

            <!-- 이벤트 관리 -->
            <li class="dropdown">옵션
                <ul class="dropdown-menu">
                    <li><a href="tmp_storage_delete.do">임시 저장소 비우기</a></li>
                </ul>
            </li>

            <!-- 로그아웃 -->
            <li><a href="userLogout.do">로그아웃</a></li>
        </ul>
    </div>
</nav>