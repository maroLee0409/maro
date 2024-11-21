/**
 * 대쉬보드 그래프 관련 로직
 */
 
 const ageGroupSalesData = {
    labels: ['10대', '20대', '30대', '40대', '50대 이상'],
    datasets: [{
        label: '매출액',
        data: [200000, 450000, 300000, 150000, 100000],  // 가상 데이터
        backgroundColor: 'rgba(75, 192, 192, 0.2)',
        borderColor: 'rgba(75, 192, 192, 1)',
        borderWidth: 1
    }]
};

new Chart(document.getElementById('ageGroupSalesChart').getContext('2d'), {
    type: 'bar',
    data: ageGroupSalesData,
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});


const categorySalesData = {
    labels: ['Electronics', 'Clothing', 'Home Appliances', 'Books'],
    datasets: [{
        label: '매출액',
        data: [500000, 300000, 200000, 100000],  // 가상 데이터
        backgroundColor: 'rgba(54, 162, 235, 0.2)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
    }]
};

new Chart(document.getElementById('categorySalesChart').getContext('2d'), {
    type: 'bar',
    data: categorySalesData,
    options: {
        scales: {
            y: {
                beginAtZero: true
            }
        }
    }
});


    // 가상 데이터
    const salesData = {
        labels: ['1월', '2월', '3월', '4월', '5월', '6월'],
        datasets: [{
            label: '총 매출',
            data: [10000, 15000, 20000, 18000, 22000, 25000],
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
        }]
    };

    const topProductsData = {
        labels: ['상품 A', '상품 B', '상품 C', '상품 D'],
        datasets: [{
            label: '판매 수량',
            data: [500, 400, 300, 200],
            backgroundColor: ['rgba(255, 99, 132, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)', 'rgba(75, 192, 192, 0.2)'],
            borderColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)'],
            borderWidth: 1
        }]
    };

    // 총 매출 그래프
    new Chart(document.getElementById('salesChart').getContext('2d'), {
        type: 'line',
        data: salesData,
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // 인기 상품 그래프
    new Chart(document.getElementById('topProductsChart').getContext('2d'), {
        type: 'bar',
        data: topProductsData,
        options: {
            indexAxis: 'x',
            scales: {
                x: {
                    beginAtZero: true
                }
            }
        }
    });

    // 최근 주문과 리뷰에 대한 가상 데이터
    const orders = [
        { orderNo: 1, customer: '김철수', amount: 50000, status: '배송중' },
        { orderNo: 2, customer: '이영희', amount: 30000, status: '완료' }
    ];

    const reviews = [
        { reviewNo: 1, product: '상품 A', customer: '김철수', rating: 5, comment: '좋아요!' },
        { reviewNo: 2, product: '상품 B', customer: '이영희', rating: 4, comment: '만족합니다.' }
    ];

    // 주문 테이블 데이터 삽입
    const ordersTableBody = document.getElementById('ordersTableBody');
    orders.forEach(order => {
        const row = `<tr>
            <td>${order.orderNo}</td>
            <td>${order.customer}</td>
            <td>${order.amount} 원</td>
            <td>${order.status}</td>
        </tr>`;
        ordersTableBody.innerHTML += row;
    });

    // 리뷰 테이블 데이터 삽입
    const reviewsTableBody = document.getElementById('reviewsTableBody');
    reviews.forEach(review => {
        const row = `<tr>
            <td>${review.reviewNo}</td>
            <td>${review.product}</td>
            <td>${review.customer}</td>
            <td>${review.rating}</td>
            <td>${review.comment}</td>
        </tr>`;
        reviewsTableBody.innerHTML += row;
    });

    // 가상 데이터
    const signupData = {
        labels: ['1월', '2월', '3월', '4월', '5월', '6월'],
        datasets: [{
            label: '가입자 수',
            data: [120, 150, 180, 200, 170, 220],
            backgroundColor: 'rgba(75, 192, 192, 0.2)',
            borderColor: 'rgba(75, 192, 192, 1)',
            borderWidth: 1
        }]
    };

    const activityData = {
        labels: ['로그인', '게시물 작성', '댓글 작성', '좋아요'],
        datasets: [{
            label: '활동량',
            data: [300, 150, 200, 180],
            backgroundColor: ['rgba(255, 99, 132, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(255, 206, 86, 0.2)', 'rgba(75, 192, 192, 0.2)'],
            borderColor: ['rgba(255, 99, 132, 1)', 'rgba(54, 162, 235, 1)', 'rgba(255, 206, 86, 1)', 'rgba(75, 192, 192, 1)'],
            borderWidth: 1
        }]
    };

    const ageDistributionData = {
        labels: ['10대', '20대', '30대', '40대', '50대 이상'],
        datasets: [{
            label: '회원 수',
            data: [50, 150, 120, 80, 30],
            backgroundColor: 'rgba(153, 102, 255, 0.2)',
            borderColor: 'rgba(153, 102, 255, 1)',
            borderWidth: 1
        }]
    };

    // 회원 가입 통계 그래프
    new Chart(document.getElementById('signupChart').getContext('2d'), {
        type: 'line',
        data: signupData,
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });

    // 회원 활동 통계 그래프
    new Chart(document.getElementById('activityChart').getContext('2d'), {
        type: 'bar',
        data: activityData,
        options: {
            indexAxis: 'x',
            scales: {
                x: {
                    beginAtZero: true
                }
            }
        }
    });

    // 연령대 분포 그래프
    new Chart(document.getElementById('ageDistributionChart').getContext('2d'), {
        type: 'pie',
        data: ageDistributionData,
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top',
                },
                tooltip: {
                    callbacks: {
                        label: function(tooltipItem) {
                            return tooltipItem.label + ': ' + tooltipItem.raw + '명';
                        }
                    }
                }
            }
        }
    });

    // 회원 테이블 데이터
    const members = [
        { memberNo: 1, name: '김철수', joinDate: '2024-01-15', status: '활성' },
        { memberNo: 2, name: '이영희', joinDate: '2024-02-20', status: '비활성' },
        { memberNo: 3, name: '박민수', joinDate: '2024-03-10', status: '활성' }
    ];

    // 회원 테이블 데이터 삽입
    const membersTableBody = document.getElementById('membersTableBody');
    members.forEach(member => {
        const row = `<tr>
            <td>${member.memberNo}</td>
            <td>${member.name}</td>
            <td>${member.joinDate}</td>
            <td>${member.status}</td>
        </tr>`;
        membersTableBody.innerHTML += row;
    });