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

window.onmousemove = (e) => {
    x = e.clientX;
    y = e.clientY;

    if (mouseDown) {
        // Debounce: 일정 시간 간격으로만 서버에 그리기 이벤트를 전송
     //   clearTimeout(debounceTimeout);
    //    debounceTimeout = setTimeout(() => {
            io.emit('draw', { x, y });
   //     }, 20); // 20ms마다 서버에 이벤트를 보내도록 설정

        ctx.lineTo(x, y);
        ctx.stroke();
    }
};
