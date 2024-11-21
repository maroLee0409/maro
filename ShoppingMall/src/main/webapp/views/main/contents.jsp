<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!-- 광고 슬라이드 섹션 -->
<section class="event-slider">
    <div class="event-slides">
        <!-- 첫 번째 슬라이드 -->
        <div class="event-slide">
            <img src="<%=request.getContextPath() %>/resources/index/img/eventBanner/eventbanner001.jpg" alt="Slide 1">
        </div>
        <!-- 두 번째 슬라이드 -->
        <div class="event-slide">
            <img src="<%=request.getContextPath() %>/resources/index/img/eventBanner/eventbanner002.jpg" alt="Slide 2">
        </div>
        <!-- 세 번째 슬라이드 -->
        <div class="event-slide">
            <img src="<%=request.getContextPath() %>/resources/index/img/eventBanner/eventbanner003.jpg" alt="Slide 3">
        </div>
    </div>
    <!-- 좌우 버튼 -->
    <button class="event-prev">&#10094;</button>
    <button class="event-next">&#10095;</button>
</section>

<!-- 신상품 섹션 -->
<section class="new-products">
    <h2 class="section-title"><label for="">New <br> Products</label></h2>
    <div class="new-products-slides-container">
        <!-- 슬라이드들 -->
        <div class="new-products-slides">
            <div class="slide">
                <img src="resources/index/img/ranking/rank1.jpg" alt="Ranking 1">
            </div>
            <div class="slide">
                <img src="resources/index/img/ranking/rank2.jpg" alt="Ranking 2">
            </div>
            <div class="slide">
                <img src="resources/index/img/ranking/rank3.jpg" alt="Ranking 3">
            </div>
            <div class="slide">
                <img src="resources/index/img/ranking/rank4.jpg" alt="Ranking 4">
            </div>
            <div class="slide">
                <img src="resources/index/img/ranking/rank5.jpg" alt="Ranking 5">
            </div>
            <div class="slide">
                <img src="resources/index/img/ranking/rank6.jpg" alt="Ranking 6">
            </div>
            <div class="slide">
                <img src="resources/index/img/ranking/rank7.jpg" alt="Ranking 7">
            </div>
                <!-- 추가 슬라이드들 -->
        </div>

        <!-- 신상품 슬라이드 좌우 버튼 -->
        <button class="new-products-prev">&#10094;</button>
        <button class="new-products-next">&#10095;</button>
    </div>
    <!-- More Details 버튼 -->
    <button class="more-details-btn">
        <span>MORE DETAILS</span>
        <i class="arrow-right"></i>
    </button>
</section>

    <!-- 추천 상품 -->
    <section class="recommended-products">
        <h2 class="section-title"><label for="">RECOMMENDED <br> PRODUCT</label></h2>

        <div class="product-category">
            <label>Consultation</label>
            <a href="popular_consultation.html" class="category-more">MORE &rarr;</a>
        </div>
        <div class="products">
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/woman_001.jpg" alt="Product 1">
                <p>Product 1</p>
                <p>₩20,000</p>
            </div>
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/woman_002.jpg" alt="Product 2">
                <p>Product 2</p>
                <p>₩30,000</p>
            </div>
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/woman_003.jpg" alt="Product 3">
                <p>Product 3</p>
                <p>₩40,000</p>
            </div>
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/woman_004.jpg" alt="Product 4">
                <p>Product 4</p>
                <p>₩50,000</p>
            </div>
        </div>

        <div class="product-category">
            <label for="">Bottom</label>
            <a href="popular_bottom.html" class="category-more">MORE &rarr;</a>
        </div>
        <div class="products">
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/woman_005.jpg" alt="Product 5">
                <p>Product 5</p>
                <p>₩20,000</p>
            </div>
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/woman_006.jpg" alt="Product 6">
                <p>Product 6</p>
                <p>₩30,000</p>
            </div>
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/woman_007.jpg" alt="Product 7">
                <p>Product 7</p>
                <p>₩40,000</p>
            </div>
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/woman_008.jpg" alt="Product 8">
                <p>Product 8</p>
                <p>₩50,000</p>
            </div>
        </div>

        <div class="product-category">
            <label for="">Shoes</label>
            <a href="popular_shoes.html" class="category-more">MORE &rarr;</a>
        </div>
        <div class="products">
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/shoes_woman001.jpg" alt="Product 5">
                <p>Product 9</p>
                <p>₩20,000</p>
            </div>
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/shoes_woman002.jpg" alt="Product 6">
                <p>Product 10</p>
                <p>₩30,000</p>
            </div>
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/shoes_woman003.jpg" alt="Product 7">
                <p>Product 11</p>
                <p>₩40,000</p>
            </div>
            <div class="product-item">
                <img src="resources/index/img/recommendedproduct/shoes_woman004.jpg" alt="Product 8">
                <p>Product 12</p>
                <p>₩50,000</p>
            </div>
        </div>

        <!-- More Details 버튼 -->
        <button class="more-details-btn">
            <span>MORE DETAILS</span>
            <i class="arrow-right"></i>
        </button>
    </section>

<!-- 리뷰 섹션 -->
<section class="reviews">
    <h2 class="section-title"><label for="">REVIEW</label></h2>

    <!-- 탭 메뉴 -->
    <div class="tabs">
        <button class="tab-btn active" data-tab="best-review">베스트 리뷰</button>
        <button class="tab-btn" data-tab="regular-review">일반 리뷰</button>
    </div>

    <!-- BEST REVIEW -->
    <div class="review-items tab-content active" id="best-review">
        <!-- BEST 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/BestReview/review001.jpg" alt="Review 1">
                <span class="heart-icon"><i class="fas fa-heart"></i></span>
            </div>
            <p class="review-title">Title 1</p>
            <p class="review-author">작성자: User1</p>
            <div class="review-meta">
                <span class="review-views">조회수: 100</span>
                <span class="review-hearts">❤️ 125</span>
            </div>
        </div>
        <!-- BEST 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/BestReview/review002.jpg" alt="Review 2">
                <span class="heart-icon"><i class="fas fa-heart"></i></span>
            </div>
            <p class="review-title">Title 1</p>
            <p class="review-author">작성자: User1</p>
            <div class="review-meta">
                <span class="review-views">조회수: 100</span>
                <span class="review-hearts">❤️ 25</span>
            </div>
        </div>
        <!-- BEST 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/BestReview/review003.jpg" alt="Review 3">
                <span class="heart-icon"><i class="fas fa-heart"></i></span>
            </div>
            <p class="review-title">Title 1</p>
            <p class="review-author">작성자: User1</p>
            <div class="review-meta">
                <span class="review-views">조회수: 100</span>
                <span class="review-hearts">❤️ 25</span>
            </div>
        </div>
        <!-- BEST 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/BestReview/review004.jpg" alt="Review 4">
                <span class="heart-icon"><i class="fas fa-heart"></i></span>
            </div>
            <p class="review-title">Title 1</p>
            <p class="review-author">작성자: User1</p>
            <div class="review-meta">
                <span class="review-views">조회수: 100</span>
                <span class="review-hearts">❤️ 25</span>
            </div>
        </div>
        <!-- BEST 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/BestReview/review005.jpg" alt="Review 1">
                <span class="heart-icon"><i class="fas fa-heart"></i></span>
            </div>
            <p class="review-title">Title 1</p>
            <p class="review-author">작성자: User1</p>
            <div class="review-meta">
                <span class="review-views">조회수: 100</span>
                <span class="review-hearts">❤️ 125</span>
            </div>
        </div>
        <!-- BEST 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/BestReview/review006.jpg" alt="Review 2">
                <span class="heart-icon"><i class="fas fa-heart"></i></span>
            </div>
            <p class="review-title">Title 1</p>
            <p class="review-author">작성자: User1</p>
            <div class="review-meta">
                <span class="review-views">조회수: 100</span>
                <span class="review-hearts">❤️ 25</span>
            </div>
        </div>
        <!-- BEST 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/BestReview/review007.jpg" alt="Review 3">
                <span class="heart-icon"><i class="fas fa-heart"></i></span>
            </div>
            <p class="review-title">Title 1</p>
            <p class="review-author">작성자: User1</p>
            <div class="review-meta">
                <span class="review-views">조회수: 100</span>
                <span class="review-hearts">❤️ 25</span>
            </div>
        </div>
        <!-- BEST 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/BestReview/review008.jpg" alt="Review 4">
                <span class="heart-icon"><i class="fas fa-heart"></i></span>
            </div>
            <p class="review-title">Title 1</p>
            <p class="review-author">작성자: User1</p>
            <div class="review-meta">
                <span class="review-views">조회수: 100</span>
                <span class="review-hearts">❤️ 25</span>
            </div>
        </div>
        <!-- 추가 리뷰 아이템들 -->
    </div>

    <!-- 일반 리뷰 -->
    <div class="review-items tab-content" id="regular-review">
        <!-- 일반 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/CommonReview/review001.jpg" alt="Review 2">
                <span class="heart-icon"><i class="far fa-heart"></i></span>
            </div>
            <p class="review-title">Title 2</p>
            <p class="review-author">작성자: User2</p>
            <div class="review-meta">
                <span class="review-views">조회수: 90</span>
                <span class="review-hearts">❤️ 15</span>
            </div>
        </div>
        <!-- 일반 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/CommonReview/review002.jpg" alt="Review 2">
                <span class="heart-icon"><i class="far fa-heart"></i></span>
            </div>
            <p class="review-title">Title 2</p>
            <p class="review-author">작성자: User2</p>
            <div class="review-meta">
                <span class="review-views">조회수: 90</span>
                <span class="review-hearts">❤️ 15</span>
            </div>
        </div>
        <!-- 일반 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/CommonReview/review003.jpg" alt="Review 2">
                <span class="heart-icon"><i class="far fa-heart"></i></span>
            </div>
            <p class="review-title">Title 2</p>
            <p class="review-author">작성자: User2</p>
            <div class="review-meta">
                <span class="review-views">조회수: 90</span>
                <span class="review-hearts">❤️ 15</span>
            </div>
        </div>
        <!-- 일반 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/CommonReview/review004.jpg" alt="Review 2">
                <span class="heart-icon"><i class="far fa-heart"></i></span>
            </div>
            <p class="review-title">Title 2</p>
            <p class="review-author">작성자: User2</p>
            <div class="review-meta">
                <span class="review-views">조회수: 90</span>
                <span class="review-hearts">❤️ 15</span>
            </div>
        </div>
        <!-- 일반 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/CommonReview/review005.jpg" alt="Review 2">
                <span class="heart-icon"><i class="far fa-heart"></i></span>
            </div>
            <p class="review-title">Title 2</p>
            <p class="review-author">작성자: User2</p>
            <div class="review-meta">
                <span class="review-views">조회수: 90</span>
                <span class="review-hearts">❤️ 15</span>
            </div>
        </div>
        <!-- 일반 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/CommonReview/review006.jpg" alt="Review 2">
                <span class="heart-icon"><i class="far fa-heart"></i></span>
            </div>
            <p class="review-title">Title 2</p>
            <p class="review-author">작성자: User2</p>
            <div class="review-meta">
                <span class="review-views">조회수: 90</span>
                <span class="review-hearts">❤️ 15</span>
            </div>
        </div>
        <!-- 일반 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/CommonReview/review007.jpg" alt="Review 2">
                <span class="heart-icon"><i class="far fa-heart"></i></span>
            </div>
            <p class="review-title">Title 2</p>
            <p class="review-author">작성자: User2</p>
            <div class="review-meta">
                <span class="review-views">조회수: 90</span>
                <span class="review-hearts">❤️ 15</span>
            </div>
        </div>
        <!-- 일반 리뷰 아이템들 -->
        <div class="review-item">
            <div class="review-image-container">
                <img src="resources/index/img/review/CommonReview/review008.jpg" alt="Review 2">
                <span class="heart-icon"><i class="far fa-heart"></i></span>
            </div>
            <p class="review-title">Title 2</p>
            <p class="review-author">작성자: User2</p>
            <div class="review-meta">
                <span class="review-views">조회수: 90</span>
                <span class="review-hearts">❤️ 15</span>
            </div>
        </div>
        <!-- 추가 리뷰 아이템들 -->
    </div>

    <!-- 페이징 -->
    <div class="pagination">
        <button class="page-btn prev-page">&lt;</button>
        <span class="page-number">1</span>
        <span class="page-number">2</span>
        <span class="page-number">3</span>
        <button class="page-btn next-page">&gt;</button>
    </div>
</section>

    