<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 로고와 검색, 로그인/카트 -->
<header>
    <div class="header-container">
        <div class="site-logo">
            <h1>LOGO</h1>
        </div>
        <div class="right-area">
            <div class="search-area">
                <input type="text" id="search-input" class="hidden" placeholder="검색어를 입력하세요">
                <button class="search-btn" id="search-icon">
                    <i class="fas fa-search"></i>
                </button>
            </div>
            <div class="user-menu">
                <a href="#"><i class="fas fa-shopping-cart"></i></a>
                <a href="#"><i class="fas fa-user"></i></a>
            </div>
        </div>
    </div>
    
    <div class="user-submenu">
        <a href="#">회원가입</a>
        <a href="#">로그인</a>
        <a href="#">COMMUNITY</a>
    </div>
</header>