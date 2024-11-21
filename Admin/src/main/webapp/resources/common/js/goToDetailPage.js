// JavaScript 함수: 클릭한 행의 ID와 eventType을 가져와서 상세 페이지로 이동
function goToDetailPage(event, options = {}) {
    const target = event.currentTarget;
    const No = target.getAttribute('data-id');
    const eventType = target.getAttribute('data-event-type'); // eventType을 읽어옴
    
    if (!No || !eventType) {
        console.error("Invalid ID or eventType: No ID or eventType found for the clicked row.");
        return;
    }

    // 기본값을 사용하여 파라미터 객체 구조 분해
    const {
        contextPath = '',
        mapping = 'detail.do',  // 기본값으로 'detail.do'를 설정
        stat = '',
        currentPage = 1,
    } = options;

    // URL 생성: 필요한 파라미터만 추가
    let url = `${mapping}?no=${encodeURIComponent(No)}&status=${encodeURIComponent(stat)}&currentPage=${encodeURIComponent(currentPage)}&eventType=${encodeURIComponent(eventType)}`;

    // 페이지 이동
    window.location.href = url;
}

// 모든 행에 클릭 이벤트 리스너 추가
document.addEventListener('DOMContentLoaded', function() {
    const rows = document.querySelectorAll('table tr[data-id]');
    
    rows.forEach(row => {
        row.addEventListener('click', function(event) {
            // 파라미터 객체를 전달 (동적으로 생성 가능)
            const options = {
                contextPath: '${contextPath}',
                mapping: 'detailForm.do',  // 이벤트 타입에 따라 다른 URL로 설정
                stat: '${status}',
                currentPage: '${pi.currentPage}',
            };
            goToDetailPage(event, options);
        });
    });
});
