/* script.js */

const scrollContainer = document.querySelector('.scroll-x');
let moving = false;
let startX;			// 기준좌표 설정
let scrollLeft;		// 동적변수

scrollContainer.addEventListener('mousedown', (e) => {	// 마우스로 좌표를 입력하면
	moving = true;
	startX = e.pageX - scrollContainer.offsetLeft;
	scrollLeft = scrollContainer.scrollLeft;
});

scrollContainer.addEventListener('mouseleave', () => {	// 마우스를 띄었을때
	moving = false;
});

scrollContainer.addEventListener('mouseup', () => {		// 마우스를 다시 갖다댔을 때 가만히 있어야함
	moving = false;
});

scrollContainer.addEventListener('mousemove', (e) => {	
	if (!moving) return;		// 마우스에 값입력이 없다면 스탑
	e.preventDefault();
	const x = e.pageX - scrollContainer.offsetLeft;
	const walk = (x - startX) * 3; // 스크롤 속도 조절
	scrollContainer.scrollLeft = scrollLeft - walk;
});
