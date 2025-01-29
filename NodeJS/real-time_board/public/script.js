let canvas = document.getElementById('canvas');
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

var io = io.connect('http://localhost:8080/');
let ctx = canvas.getContext("2d");

let x;
let y;
let mouseDown = false;

// Debouncing을 위한 변수 설정
let debounceTimeout;

window.onmousedown = (e) => {
    ctx.moveTo(x, y);
    io.emit('down',{x ,y }) 
    // io.emit은 서버로 이벤트를 전송,
    // 서버와 이벤트 핸들러가 동일한  socket.on('down', (data)가 실행됨.
    mouseDown = true;
};

window.onmouseup = (e) => {
    mouseDown = false;
};

// 서버로 그리기 이벤트 전송
io.on('ondraw', ({ x, y }) => {
    ctx.lineTo(x, y);
    ctx.stroke();
});


io.on('ondown',({x,y}) =>{
    ctx.moveTo(x,y);
})
// 서버 전체에 대한 이벤트 리스너

window.onmousemove = (e) => {
    x = e.clientX;
    y = e.clientY;

    if (mouseDown) {
        io.emit('draw', { x, y });
        ctx.lineTo(x, y);
        ctx.stroke();
    }
};

/*
moveTo(x, y): 새로운 시작점을 (x, y)로 이동.
lineTo(x, y): 현재 위치에서 (x, y)로 선을 그립니다.
stroke(): 설정된 경로를 실제로 화면에 그려줍니다
*/