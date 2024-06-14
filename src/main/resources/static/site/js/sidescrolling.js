/* script.js */

const scrollContainer = document.querySelector('.scroll-x');
let isDown = false;
let startX;
let scrollLeft;

scrollContainer.addEventListener('mousedown', (e) => {
	isDown = true;
	startX = e.pageX - scrollContainer.offsetLeft;
	scrollLeft = scrollContainer.scrollLeft;
});

scrollContainer.addEventListener('mouseleave', () => {
	isDown = false;
});

scrollContainer.addEventListener('mouseup', () => {
	isDown = false;
});

scrollContainer.addEventListener('mousemove', (e) => {
	if (!isDown) return;
	e.preventDefault();
	const x = e.pageX - scrollContainer.offsetLeft;
	const walk = (x - startX) * 3; // 스크롤 속도 조절
	scrollContainer.scrollLeft = scrollLeft - walk;
});
