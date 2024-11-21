<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fm" %>

<script type="text/javascript">
//JavaScript 함수: 필터 UI 표시/숨기기
function toggleFilter() {
    const filterContainer = document.querySelector('.filter-container');
    const mainSearchBtn = document.querySelector('.search-btn-main');
    const filterSearchBtn = document.querySelector('.search-btn-filter');

    if (filterContainer.style.display === 'none' || filterContainer.style.display === '') {
        filterContainer.style.display = 'block';
        mainSearchBtn.style.display = 'none';  // 원래 위치의 검색 버튼 숨김
        filterSearchBtn.style.display = 'inline-block';  // 필터 내 검색 버튼 표시
    } else {
        filterContainer.style.display = 'none';
        mainSearchBtn.style.display = 'inline-block';  // 원래 위치의 검색 버튼 표시
        filterSearchBtn.style.display = 'none';  // 필터 내 검색 버튼 숨김
    }
}

// 필터 체크박스 상태에 따라 필터 항목을 표시/숨김
function toggleFilterItem(checkbox, filterId) {
    const filterElement = document.getElementById(filterId);
    if (checkbox.checked) {
        filterElement.style.display = 'block';
    } else {
        filterElement.style.display = 'none';
    }
}

// 페이지 로드 시 체크박스 상태에 따라 필터 항목을 초기화
window.onload = function() {
    const filters = [
        { checkboxId: 'categoryCheckbox', filterId: 'categoryFilterItem', value: '${filter.categoryNo}' },
        { checkboxId: 'userNoCheckbox', filterId: 'userNoFilterItem', value: '${filter.userNo}' },
        { checkboxId: 'viewsCheckbox', filterId: 'viewsFilterItem', value: '${filter.minViews}${filter.maxViews}' },
        { checkboxId: 'dateCheckbox', filterId: 'dateFilterItem', value: '${filter.startDate}${filter.endDate}' },
        { checkboxId: 'statusCheckbox', filterId: 'statusFilterItem', value: '${filter.isDeleted}' },
        { checkboxId: 'commentCheckbox', filterId: 'commentFilterItem', value: '${filter.sortByCommentCount}' }
    ];

    filters.forEach(filter => {
        const checkbox = document.getElementById(filter.checkboxId);
        if (filter.value !== '') {
            checkbox.checked = true;
            toggleFilterItem(checkbox, filter.filterId);
        } else {
            checkbox.checked = false;
            toggleFilterItem(checkbox, filter.filterId);
        }
    });
}
</script>

<div class="search-container">
    <form action="boardSearchList.do" method="get">
        <input type="hidden" name="subtitle" value="${param.subtitle}">
        <div class="content-title form-group">
            <div class="search-bar">
                <i class="fa-solid fa-magnifying-glass search-icon"></i>
                <input type="search" name="searchKeyword" class="search-input" placeholder="검색" value="${searchKeyword}">
            </div>
            <button type="button" class="btn filter-icon" onclick="toggleFilter()">
                <span class="material-symbols-outlined">tune</span>
            </button>
        </div>

        <!-- 필터 UI 추가 -->
        <div id="filterContainer" class="filter-container">

            <!-- 필터 선택 체크박스들 -->
            <div class="filter-item">
                <label><input type="checkbox" id="categoryCheckbox" onclick="toggleFilterItem(this, 'categoryFilterItem')"> 카테고리</label>
            </div>
            <div class="filter-item">
                <label><input type="checkbox" id="userNoCheckbox" onclick="toggleFilterItem(this, 'userNoFilterItem')"> 사용자 번호</label>
            </div>
            <div class="filter-item">
                <label><input type="checkbox" id="viewsCheckbox" onclick="toggleFilterItem(this, 'viewsFilterItem')"> 조회수</label>
            </div>
            <div class="filter-item">
                <label><input type="checkbox" id="dateCheckbox" onclick="toggleFilterItem(this, 'dateFilterItem')"> 작성일</label>
            </div>
            <div class="filter-item">
                <label><input type="checkbox" id="statusCheckbox" onclick="toggleFilterItem(this, 'statusFilterItem')"> 상태</label>
            </div>
            <div class="filter-item">
                <label><input type="checkbox" id="commentCheckbox" onclick="toggleFilterItem(this, 'commentFilterItem')"> 댓글 수 정렬</label>
            </div>

            <!-- 필터 항목들 -->
            <div id="categoryFilterItem" class="filter-item" style="display:none;">
                <label for="categoryFilter" class="filter-label filter-category-label">카테고리</label>
                <select id="categoryFilter" name="categoryNo" class="filter-select">
                    <option value="">전체</option>
                    <c:forEach var="category" items="${categoryList}">
                        <option value="${category.categoryNo}" <c:if test="${category.categoryNo == filter.categoryNo}">selected</c:if>>${category.name}</option>
                    </c:forEach>
                </select>
            </div>

            <div id="userNoFilterItem" class="filter-item" style="display:none;">
                <label for="userNoFilter" class="filter-label">사용자 번호</label>
                <input type="number" id="userNoFilter" name="userNo" class="filter-input" placeholder="사용자 번호" value="${filter.userNo}">
            </div>

            <div id="viewsFilterItem" class="filter-item" style="display:none;">
                <label for="minViewsFilter" class="filter-label">최소 조회수</label>
                <input type="number" id="minViewsFilter" name="minViews" class="filter-input" placeholder="최소 조회수" value="${filter.minViews}">
                <label for="maxViewsFilter" class="filter-label">최대 조회수</label>
                <input type="number" id="maxViewsFilter" name="maxViews" class="filter-input" placeholder="최대 조회수" value="${filter.maxViews}">
            </div>

            <div id="dateFilterItem" class="filter-item" style="display:none;">
                <label for="startDateFilter" class="filter-label">작성일 시작</label>
                <input type="date" id="startDateFilter" name="startDate" class="filter-input" value="${filter.startDate}">
                <label for="endDateFilter" class="filter-label">작성일 종료</label>
                <input type="date" id="endDateFilter" name="endDate" class="filter-input" value="${filter.endDate}">
            </div>

            <div id="statusFilterItem" class="filter-item" style="display:none;">
                <label for="statusFilter" class="filter-label filter-status-label">상태</label>
                <select id="statusFilter" name="isDeleted" class="filter-select">
                    <option value="">전체</option>
                    <option value="N" <c:if test="${filter.isDeleted == 'N'}">selected</c:if>>정상</option>
                    <option value="Y" <c:if test="${filter.isDeleted == 'Y'}">selected</c:if>>삭제됨</option>
                </select>
            </div>

            <div id="commentFilterItem" class="filter-item" style="display:none;">
                <label for="sortByCommentCount" class="filter-label">댓글 수 정렬</label>
                <select id="sortByCommentCount" name="sortByCommentCount" class="filter-select">
                    <option value="">정렬 안함</option>
                    <option value="true" <c:if test="${filter.sortByCommentCount == true}">selected</c:if>>댓글 수 많은 순</option>
                </select>
            </div>

            <!-- 검색 버튼을 필터 컨테이너 안으로 이동 -->
            <div class="filter-item filter-btn-container">
                <button type="submit" class="btn search-btn search-btn-filter btn-w btn-h">검색</button>
            </div>
        </div>
    </form>
</div>
