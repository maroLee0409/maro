/* 최상단 검색 돋보기 기능 */
function searchButtonToggle(){
    // 돋보기 버튼 클릭 시 검색창 토글
    document.getElementById('search-icon').addEventListener('click', function() {
        const searchArea = document.querySelector('.search-area');
        const searchInput = document.getElementById('search-input');
        
        if (searchInput.classList.contains('hidden')) {
            searchInput.classList.remove('hidden');
            searchArea.classList.add('show');
        } else {
            searchInput.classList.add('hidden');
            searchArea.classList.remove('show');
        }
    });
}

/* 메인 베너 슬라이더 기능 */
function initializeMainSlider() {
    let slideIndex = 0;
    const slides = document.querySelector('.slider .slides');
    const totalSlides = document.querySelectorAll('.slider .slides img').length;

    function showSlide(index) {
        if (index >= totalSlides) {
            slideIndex = 0;
        } else if (index < 0) {
            slideIndex = totalSlides - 1;
        }
        const offset = -slideIndex * 100; // 슬라이드 이동 값 계산
        slides.style.transform = `translateX(${offset}vw)`; 
    }

    function moveSlide(n) {
        slideIndex += n;
        showSlide(slideIndex);
    }

    // 자동 슬라이드 (3초마다 실행)
    setInterval(function() {
        moveSlide(1); // 오른쪽으로 이동
    }, 3000);

    // 좌우 버튼 이벤트
    document.querySelector('.main-prev').addEventListener('click', function() {
        moveSlide(-1);
    });

    document.querySelector('.main-next').addEventListener('click', function() {
        moveSlide(1);
    });

    // 초기 슬라이드 표시
    showSlide(slideIndex);
}
/* 새 상품 슬라이더 기능 */
function initializeNewProductsSlider() {
    let slideIndex = 0;
    const slides = document.querySelectorAll('.new-products-slides .slide');
    const totalSlides = slides.length;
    const slidesToShow = 4;
    const slideWidth = 280;
    const containerWidth = (slideWidth + 15) * slidesToShow;
    let autoSlideInterval;

    document.querySelector('.new-products-slides-container').style.width = `${containerWidth}px`;

    function showSlide(index) {
        if (index >= totalSlides) {
            slideIndex = 0;
        } else if (index < 0) {
            slideIndex = totalSlides - slidesToShow;
        }
        const offset = -slideIndex * (slideWidth + 15);
        document.querySelector('.new-products-slides').style.transform = `translateX(${offset}px)`;
    }

    function moveSlide(step) {
        slideIndex += step;
        if (slideIndex > totalSlides - slidesToShow) {
            slideIndex = 0;
        } else if (slideIndex < 0) {
            slideIndex = totalSlides - slidesToShow;
        }
        showSlide(slideIndex);
    }

    document.querySelector('.new-products-prev').addEventListener('click', function() {
        moveSlide(-1);
        resetAutoSlide();
    });

    document.querySelector('.new-products-next').addEventListener('click', function() {
        moveSlide(1);
        resetAutoSlide();
    });

    function startAutoSlide() {
        autoSlideInterval = setInterval(function() {
            moveSlide(1);
        }, 5000);
    }

    function stopAutoSlide() {
        clearInterval(autoSlideInterval);
    }

    function resetAutoSlide() {
        stopAutoSlide();
        startAutoSlide();
    }

    showSlide(slideIndex);
    startAutoSlide();

    document.querySelector('.new-products-slides-container').addEventListener('mouseover', stopAutoSlide);
    document.querySelector('.new-products-slides-container').addEventListener('mouseout', startAutoSlide);
}

/* 이벤트 베너 슬라이더 기능 */
function initializeEventSlider() {
    let slideIndex = 0;
    const slideWidth = 1400; // 슬라이드의 고정 너비 (px)
    const slides = document.querySelector('.event-slider .event-slides');
    const totalSlides = document.querySelectorAll('.event-slider .event-slides img').length;

    function showSlide(index) {
        if (index >= totalSlides) {
            slideIndex = 0;
        } else if (index < 0) {
            slideIndex = totalSlides - 1;
        }
        const offset = -slideIndex * slideWidth; // 슬라이드 이동 값 계산 (px 단위)
        slides.style.transform = `translateX(${offset}px)`; 
    }

    function moveSlide(n) {
        slideIndex += n;
        showSlide(slideIndex);
    }

    // 자동 슬라이드 (3초마다 실행)
    setInterval(function() {
        moveSlide(1); // 오른쪽으로 이동
    }, 3000);

    // 좌우 버튼 이벤트
    document.querySelector('.event-prev').addEventListener('click', function() {
        moveSlide(-1);
    });

    document.querySelector('.event-next').addEventListener('click', function() {
        moveSlide(1);
    });

    // 초기 슬라이드 표시
    showSlide(slideIndex);
}

/* 리뷰 섹션 탭 기능 */
function initializeTabButton(){
        // 탭 기능
        const tabButtons = document.querySelectorAll('.tab-btn');
        const tabContents = document.querySelectorAll('.tab-content');
    
        tabButtons.forEach(btn => {
            btn.addEventListener('click', function() {
                tabButtons.forEach(b => b.classList.remove('active'));
                tabContents.forEach(content => content.classList.remove('active'));
    
                btn.classList.add('active');
                document.getElementById(btn.getAttribute('data-tab')).classList.add('active');
            });
        });
    
        // 페이징 기능 (기본적으로는 작동하지 않지만, 나중에 페이지 이동 처리 가능)
        const pageNumbers = document.querySelectorAll('.page-number');
    
        pageNumbers.forEach(page => {
            page.addEventListener('click', function() {
                pageNumbers.forEach(p => p.classList.remove('active'));
                page.classList.add('active');
                // 페이지 변경 시 데이터 업데이트 로직 추가 필요
            });
        });
}

// 스크롤시 따라다니는 메뉴바
function fixedMenuBar(){
    const categoryMenu = document.querySelector('.category-menu'); // 원래 메뉴바
    const fixedCategoryMenu = document.querySelector('.fixed-category-menu'); // 고정 메뉴바
    let categoryMenuOffset = categoryMenu.offsetTop + categoryMenu.offsetHeight; // 원래 메뉴바의 하단 위치 계산

    // 스크롤 이벤트 감지
    window.addEventListener('scroll', throttle(function() {
        if (window.scrollY > categoryMenuOffset) {
            fixedCategoryMenu.classList.remove('hidden'); // 스크롤 시 고정 메뉴바 표시
        } else {
            fixedCategoryMenu.classList.add('hidden'); // 원래 메뉴바가 보이면 고정 메뉴바 숨김
        }
    }, 100)); // throttle: 100ms 간격으로 실행
    
    // 윈도우 리사이즈 시 메뉴바 위치 재계산
    window.addEventListener('resize', function() {
        categoryMenuOffset = categoryMenu.offsetTop + categoryMenu.offsetHeight;
    });
}
// 스크롤 시 따라다니는 아이콘 메뉴 보이기/숨기기 기능
function floatingButtonVisibility() {
    const floatingIcons = document.querySelector('.floating-icons');
    const categoryMenu = document.querySelector('.category-menu');
    
    // category-menu 하단의 위치를 구함
    const categoryMenuOffset = categoryMenu.offsetTop + categoryMenu.offsetHeight;

    window.addEventListener('scroll', throttle(function() {
        if (window.scrollY > categoryMenuOffset) {
            floatingIcons.classList.add('show');  // 특정 구간 이후로 스크롤 시 아이콘 메뉴 보이기
        } else {
            floatingIcons.classList.remove('show');  // 그 전에는 숨김
        }
    }, 100));  // 100ms의 간격으로 스크롤 이벤트 실행
}

// 스크롤 이벤트 최적화를 위한 throttle 함수
function throttle(func, limit) {
    let lastFunc;
    let lastRan;
    return function() {
        const context = this;
        const args = arguments;
        if (!lastRan) {
            func.apply(context, args);
            lastRan = Date.now();
        } else {
            clearTimeout(lastFunc);
            lastFunc = setTimeout(function() {
                if ((Date.now() - lastRan) >= limit) {
                    func.apply(context, args);
                    lastRan = Date.now();
                }
            }, limit - (Date.now() - lastRan));
        }
    }
}


// 위로 이동 기능 구현
function scrollToTop() {
    window.scrollTo({
        top: 0,
        behavior: 'smooth' // 부드러운 스크롤 애니메이션
    });
}

// 아래로 이동 기능 구현
function scrollToBottom() {
    window.scrollTo({
        top: document.body.scrollHeight, // 문서의 전체 높이로 스크롤
        behavior: 'smooth' // 부드러운 스크롤 애니메이션
    });
}

// 아이콘 클릭 이벤트 리스너 등록
function initializeUpDownButtons() {
    const upButton = document.querySelector('.fa-angle-up');
    const downButton = document.querySelector('.fa-angle-down');

    // 위로 가기 버튼 클릭 시 동작
    upButton.addEventListener('click', function() {
        scrollToTop();
    });

    // 아래로 가기 버튼 클릭 시 동작
    downButton.addEventListener('click', function() {
        scrollToBottom();
    });
}


document.addEventListener('DOMContentLoaded', function() {
   
    initializeMainSlider();   // 메인 슬라이드 초기화
    initializeNewProductsSlider(); // 신상품 슬라이드 초기화
    initializeEventSlider(); // 이벤트 슬라이드 초기화
    initializeTabButton(); // 탭 버튼 초기화
    searchButtonToggle();   // 검색 기능 토글
    fixedMenuBar(); // 스크롤시 따라다니는 메뉴바
    floatingButtonVisibility();  // 아이콘 메뉴 보이기/숨기기 초기화
    initializeUpDownButtons(); // 위/아래 버튼 초기화
});