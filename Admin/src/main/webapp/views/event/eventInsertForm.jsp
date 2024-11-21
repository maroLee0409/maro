<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>이벤트 등록</title>
    <link rel="stylesheet" href="${contextPath}/resources/master.css">

    <style>
        h2 {
            margin-bottom: 20px;
            color: #333;
            font-size: 28px;
            text-align: center;
        }
        .event-form {
            display: none;
        }

        .event-form.active {
            display: block;
        }

        small {
            font-size: 12px;
            color: #888;
        }

        /* 숨겨진 상태의 알람 메세지 */
        .alertMessage {
            display: none;
            font-size: 14px;
            margin-top: 15px;
            margin-bottom: 15px;
        }

        /* 긍정의 메세지 */
        .alert-success {
            color: green;
        }

        /* 부정의 메세지 */
        .alert-error {
            color: red;
        }
    </style>

    <script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function() {
        const eventForms = document.querySelectorAll('.event-form');
        const alertMessageElement = document.querySelector('.alertMessage');
        const statusInput = document.querySelector('input[name="status"]');  // hidden input
        const statusDisplay = document.getElementById('statusDisplay');  // 상태 표시용 span
        const startDateInput = document.querySelector('input[name="startDate"]');
        const endDateInput = document.querySelector('input[name="endDate"]');

        // 이벤트 유형 변경 시 폼 변경
        document.querySelectorAll('input[name="eventType"]').forEach(function(radio) {
            radio.addEventListener('change', function() {
                eventForms.forEach(function(form) {
                    form.style.display = 'none';
                });
                document.getElementById(this.value + "Form").style.display = 'block';
            });
        });

        // 시작 날짜 또는 종료 날짜가 변경될 때 STATUS 상태를 실시간으로 업데이트
        startDateInput.addEventListener('change', updateStatus);
        endDateInput.addEventListener('change', updateStatus);

        function updateStatus() {
            const startDate = startDateInput.value;
            const endDate = endDateInput.value;

            // 날짜 값이 없을 경우 STATUS 초기 설정
            if (!startDate || !endDate) {
                statusInput.value = 'SCHEDULED';
                statusDisplay.textContent = '예정';
                statusDisplay.style.color = 'black';  // 검은색
                return;
            }

            // 오늘 날짜 (시간 제거)
            const today = new Date();
            today.setHours(0, 0, 0, 0);  // 시간 정보를 0으로 설정

            // 시작 날짜와 종료 날짜도 Date 객체로 변환 (시간 제거)
            const start = new Date(startDate);
            start.setHours(0, 0, 0, 0);

            const end = new Date(endDate);
            end.setHours(0, 0, 0, 0);

            // STATUS 상태 자동 설정 및 시각적 표시
            if (start > today) {
                statusInput.value = 'SCHEDULED';  // 시작 날짜가 오늘 이후
                statusDisplay.textContent = '예정';
                statusDisplay.style.color = 'black';  // 검은색
                statusDisplay.style.fontweight = "bold";
            } else if (end < today) {
                statusInput.value = 'ENDED';  // 종료 날짜가 오늘 이전
                statusDisplay.textContent = '종료';
                statusDisplay.style.color = 'red';  // 붉은색
                statusDisplay.style.fontweight = "bold";
            } else {
                statusInput.value = 'ACTIVE';  // 현재 진행 중
                statusDisplay.textContent = '진행';
                statusDisplay.style.color = 'green';  // 초록색
                statusDisplay.style.fontweight = "bold";
            }

            console.log('STATUS 설정:', statusInput.value);  // STATUS 값 확인
        }

        // 폼 제출 시 유효성 검사
        document.querySelector('form').addEventListener('submit', function(event) {
            const eventType = document.querySelector('input[name="eventType"]:checked').value;

            const startDate = startDateInput.value;
            const endDate = endDateInput.value;

            if (!startDate || !endDate) {
                alert('시작 날짜와 종료 날짜를 입력해야 합니다.');
                event.preventDefault();
                return;
            }

            // 날짜 유효성 검사
            if (new Date(startDate) > new Date(endDate)) {
                alert('시작 날짜는 종료 날짜보다 이후일 수 없습니다.');
                event.preventDefault();
                return;
            }

            if (eventType === 'coupon') {
                const discountAmount = document.querySelector('input[name="discountAmount"]').value;
                const discountPercent = document.querySelector('input[name="discountPercent"]').value;

                if (!discountAmount && !discountPercent) {
                    alert('할인 금액 또는 할인율 중 하나는 입력해야 합니다.');
                    event.preventDefault();
                    return;
                }
            }
        });
    });


    </script>
</head>
<body>

    <div class="content">
        <h2>이벤트 등록</h2>

        <form action="eventInsertOk.do" method="post" enctype="multipart/form-data">
            <!-- 공통 이벤트 정보 -->
            <div class="form-group">
                <label>이벤트 이름:</label>
                <input type="text" name="eventName" required>
            </div>

            <div class="form-group">
                <label>이벤트 설명:</label>
                <textarea name="eventDescription" required></textarea>
            </div>

            <div class="form-group">
                <label>시작 날짜:</label>
                <input type="date" name="startDate" required>
            </div>

            <div class="form-group">
                <label>종료 날짜:</label>
                <input type="date" name="endDate" required>
            </div>

            <!-- 이벤트 상태 -->
			<div class="form-group">
			    <h3>이벤트 상태</h3>
			    <span id="statusDisplay" style="color: black;">예정</span>
			    <input type="hidden" name="status" value="SCHEDULED">
			</div>


            <!-- 이벤트 유형 선택 -->
            <h3>이벤트 유형 선택</h3>
            <div class="form-group">
                <label>
                    <input type="radio" name="eventType" value="GENERAL" checked> 일반 이벤트
                </label>
                <label>
                    <input type="radio" name="eventType" value="BANNER"> 베너 이벤트
                </label>
                <label>
                    <input type="radio" name="eventType" value="COUPON"> 쿠폰 이벤트
                </label>
            </div>

            <!-- 일반 이벤트 폼 -->
            <div id="generalForm" class="event-form active">
                <p>일반 이벤트는 추가 정보가 필요하지 않습니다.</p>
            </div>

            <!-- 베너 이벤트 폼 -->
            <div id="bannerForm" class="event-form">
                <h3>베너 이벤트</h3>
                <div class="form-group">
                    <label>배너 링크 URL:</label>
                    <input type="text" name="linkUrl">
                </div>
                <div class="form-group">
                    <label>배너 이미지:</label>
                    <input type="file" name="bannerImage" accept="image/jpeg, image/png, image/gif">
                </div>
                <small>이미지 크기는 1400x150 픽셀이어야 하며, 최대 크기는 2MB입니다.</small>
            </div>

            <!-- 쿠폰 이벤트 폼 -->
            <div id="couponForm" class="event-form">
                <h3>쿠폰 이벤트</h3>
                <div class="form-group">
                    <label>쿠폰 코드:</label>
                    <input type="text" name="couponCode" placeholder="자동 생성하려면 클릭하세요">
                </div>
                <div class="alertMessage">
                    <!-- 중복 체크 결과 메시지가 여기에 표시됨 -->
                </div>
                <div class="form-group">
                    <label>할인 금액:</label>
                    <input type="number" name="discountAmount" placeholder="금액을 입력하거나">
                </div>
                <div class="form-group">
                    <label>할인율:</label>
                    <input type="number" name="discountPercent" placeholder="할인율을 입력하세요">
                </div>
                <div class="form-group">
                    <label>쿠폰 만료 날짜:</label>
                    <input type="date" name="expiryDate">
                </div>
            </div>

            <!-- 등록 버튼 -->
            <input class="btn" type="submit" value="이벤트 등록">
        </form>
    </div>

</body>
</html>
