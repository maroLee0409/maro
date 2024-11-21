<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="${ contextPath }/resources/dashboard/css/dashboard.css" rel="stylesheet" type="text/css"/>
</head>
<body>

<div class="dashboard-container">
    <!-- 회원 관련 섹션 -->
    <div class="section wide-section">
        <h2>회원 가입 통계</h2>
        <div class="dashboard-card">
            <div class="chart-container">
                <canvas id="signupChart"></canvas>
            </div>
        </div>
    </div>
    <div class="section wide-section">
        <h2>회원 활동 통계</h2>
        <div class="dashboard-card">
            <div class="chart-container">
                <canvas id="activityChart"></canvas>
            </div>
        </div>
    </div>
    <div class="section wide-section">
        <h2>회원 목록</h2>
        <div class="dashboard-card">
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>회원 번호</th>
                            <th>이름</th>
                            <th>가입일</th>
                            <th>상태</th>
                        </tr>
                    </thead>
                    <tbody id="membersTableBody">
                        <!-- 데이터가 삽입될 부분 -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <!-- 매출 관련 섹션 -->
    <div class="section wide-section">
        <h2>총 매출</h2>
        <div class="dashboard-card">
            <div class="chart-container">
                <canvas id="salesChart"></canvas>
            </div>
        </div>
    </div>
    <div class="section wide-section">
        <h2>인기 상품</h2>
        <div class="dashboard-card">
            <div class="chart-container">
                <canvas id="topProductsChart"></canvas>
            </div>
        </div>
    </div>
    <div class="section wide-section">
        <h2>상품 카테고리별 매출</h2>
        <div class="dashboard-card">
            <div class="chart-container">
                <canvas id="categorySalesChart"></canvas>
            </div>
        </div>
    </div>
    <div class="section wide-section">
        <h2>고객 연령대별 매출</h2>
        <div class="dashboard-card">
            <div class="chart-container">
                <canvas id="ageGroupSalesChart"></canvas>
            </div>
        </div>
    </div>
    
    <!-- 최근 주문 및 리뷰 섹션 -->
    <div class="section wide-section">
        <h2>최근 주문</h2>
        <div class="dashboard-card">
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>주문 번호</th>
                            <th>고객</th>
                            <th>주문 금액</th>
                            <th>상태</th>
                        </tr>
                    </thead>
                    <tbody id="ordersTableBody">
                        <!-- 데이터가 삽입될 부분 -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <div class="section wide-section">
        <h2>최근 리뷰</h2>
        <div class="dashboard-card">
            <div class="table-container">
                <table>
                    <thead>
                        <tr>
                            <th>리뷰 번호</th>
                            <th>상품</th>
                            <th>고객</th>
                            <th>평점</th>
                            <th>내용</th>
                        </tr>
                    </thead>
                    <tbody id="reviewsTableBody">
                        <!-- 데이터가 삽입될 부분 -->
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script src="${contextPath}/resources/dashboard/js/dashboard.js"></script>

</body>
</html>