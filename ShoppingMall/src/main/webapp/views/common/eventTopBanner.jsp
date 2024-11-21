<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 이벤트 배너 -->
<div class="event_banner">
    <div class="slider-container">
        <ul id="eventListContainer">
            <!-- 이벤트 리스트를 출력할 부분 -->
        </ul>
    </div>
</div>

<!-- CSS로 슬라이드 애니메이션 구현 -->
<style>
    .slider-container {
        overflow: hidden;
        height: 20px;  /* 슬라이드 높이 설정 */
        position: relative;
        width: 100%;   /* 컨테이너 너비 */
    }

    #eventListContainer {
        list-style: none;
        padding: 0;
        margin: 0;
        position: absolute;
        width: 100%;
    }

    #eventListContainer li {
        font-size: 14px;
        height: 20px;  /* 각 아이템의 높이를 슬라이드 높이와 맞춤 */
        line-height: 20px;  /* 수직 가운데 정렬을 위한 line-height 설정 */
        text-align: center;  /* 글씨를 가로로 가운데 정렬 */
        color: yellow;  /* 기본 글씨 색은 노란색 */
        animation: rainbowColor 4s infinite alternate;  /* 무지개색 애니메이션 적용 */
    }

    @keyframes rainbowColor {
        0% { color: red; }
        40% { color: orange; }
        70% { color: yellow; }
        100% { color: violet; }
    }
</style>

<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function() {
        // Admin 프로젝트에서 이벤트 리스트 API 호출
        fetch('http://localhost:8585/Admin/eventListApi.do?eventType=EVENT')  // Admin 프로젝트의 API URL
            .then(function(response) {
                if (!response.ok) {
                    throw new Error("API 호출에 실패했습니다.");
                }
                return response.json();  // JSON 형식으로 변환
            })
            .then(function(data) {
                var eventListContainer = document.getElementById('eventListContainer');
                
                if (data.length === 0) {
                    // 이벤트가 없을 때 표시할 메시지
                    var noEventHtml = '<li>현재 진행중인 이벤트가 없습니다.</li>';
                    eventListContainer.innerHTML = noEventHtml;
                } else {
                    // 이벤트가 있을 경우, 목록 출력
                    data.forEach(function(event) {
                        var eventHtml = '<li>' + event.name + '</li>';  // 이벤트 이름을 <li> 요소로 출력
                        eventListContainer.innerHTML += eventHtml;
                    });

                    // 슬라이드 애니메이션 시작
                    startSlide();
                }
            })
            .catch(function(error) {
                console.error("이벤트 목록을 가져오는 중 오류 발생: ", error);
                var eventListContainer = document.getElementById('eventListContainer');
                eventListContainer.innerHTML = '<li>현재 진행중인 이벤트가 없습니다.</li>';  // 오류 발생 시 메시지 출력
            });

        // 슬라이드 애니메이션 함수
        function startSlide() {
            var eventListContainer = document.getElementById('eventListContainer');
            var items = eventListContainer.querySelectorAll('li');
            var itemHeight = items[0].clientHeight;  // 첫 번째 아이템의 높이를 계산
            var index = 0;

            setInterval(function() {
                eventListContainer.style.transition = 'transform 1s ease-in-out';
                eventListContainer.style.transform = 'translateY(-' + index * itemHeight + 'px)';  // 슬라이드 높이 맞춤

                index++;

                // 마지막 항목에 도달하면 처음으로 돌아감
                if (index === items.length) {
                    setTimeout(function() {
                        eventListContainer.style.transition = 'none';  // 애니메이션 제거
                        eventListContainer.style.transform = 'translateY(0)';  // 처음으로 돌아감
                        index = 0;
                        // 원래의 애니메이션 효과 재적용
                        setTimeout(function() {
                            eventListContainer.style.transition = 'transform 1s ease-in-out';
                        }, 100);  // 0.1초 딜레이 후 애니메이션 다시 적용
                    }, 2000);  // 마지막 슬라이드가 끝난 후 2초 후에 처음으로 돌아감
                }
            }, 2000);  // 2초마다 슬라이드
        }
    });
</script>
