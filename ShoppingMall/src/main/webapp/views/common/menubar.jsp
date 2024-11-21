
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
<!-- 메뉴바 -->
<nav class="category-menu">
	<ul>
		<li>
			<button>
				<div class="menu-text">
					<span class="title">MAN</span> <span class="more">MORE →</span>
				</div>
				<div class="menu-image">
					<img src="<%=request.getContextPath() %>/resources/index/img/category-menu/man_shirts 100x100.jpg"
						alt="MAN">
				</div>
			</button>
		</li>
		<li>
			<button>
				<div class="menu-text">
					<span class="title">WOMAN</span> <span class="more">MORE →</span>
				</div>
				<div class="menu-image">
					<img
						src="<%=request.getContextPath() %>/resources/index/img/category-menu/woman_shirt 100x100.jpg"
						alt="WOMAN">
				</div>
			</button>
		</li>
		<li>
			<button>
				<div class="menu-text">
					<span class="title">DIGITAL</span> <span class="more">MORE →</span>
				</div>
				<div class="menu-image">
					<img src="<%=request.getContextPath() %>/resources/index/img/category-menu/digit_100x100.jpg"
						alt="DIGITAL">
				</div>
			</button>
		</li>
		<li>
			<button>
				<div class="menu-text">
					<span class="title">EVENT</span> <span class="more">MORE →</span>
				</div>
				<div class="menu-image">
					<img src="<%=request.getContextPath() %>/resources/index/img/category-menu/event.jpg" alt="EVENT">
				</div>
			</button>
		</li>
		<li>
			<button>
				<div class="menu-text">
					<span class="title">MAGAZINE</span> <span class="more">MORE
						→</span>
				</div>
				<div class="menu-image">
					<img src="<%=request.getContextPath() %>/resources/index/img/category-menu/magagin.jpg"
						alt="MAGAZINE">
				</div>
			</button>
		</li>
		<li>
			<button>
				<div class="menu-text">
					<span class="title">STYLIST</span> <span class="more">MORE →</span>
				</div>
				<div class="menu-image">
					<img src="<%=request.getContextPath() %>/resources/index/img/category-menu/stylish.jpg"
						alt="STYLIST">
				</div>
			</button>
		</li>
	</ul>
</nav>