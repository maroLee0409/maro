/**
 * 
 */
 
// index.js
window.onload = function() {
    const images = ['bg-image-1', 'bg-image-2', 'bg-image-3'];
    let currentIndex = 0;
    const interval = 3000; // 3초 간격

    function changeBackground() {
        document.body.classList.remove(images[currentIndex]);
        currentIndex = (currentIndex + 1) % images.length;
        document.body.classList.add(images[currentIndex]);
    }

    setInterval(changeBackground, interval);
};
