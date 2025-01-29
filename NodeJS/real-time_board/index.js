import express from 'express'; 

import http from 'http';
import { Server } from 'socket.io';

// express는 HTTP 요청을 관리하는 프레임워크.
// http 모듈은 실제로 서버를 실행하는 기능을 제공.
// socket.io는 실시간 통신을 처리하기 위해 http 서버 위에서 동작해야 하기 때문에 
// http와 함께 사용되는 것입니다.

let app = express();
// express 모듈을 호출하여 새로운 애플리케이션 인스턴스를 생성하고, 그 인스턴스를 app 변수에 저장합니다
// 이 app은 이제 HTTP 요청을 처리할 수 있는 객체로, 예를 들어 요청을 처리할 라우터를 설정하거나 미들웨어를 추가
let httpServer = http.createServer(app);
// http 모듈로 HTTP 서버를 생성, 이 서버가 app의 요청 처리 로직을 사용하도록 설정
// httpServer는 이제 express가 설정한 대로 HTTP 요청을 처리하는 서버가 됨.
let io = new Server(httpServer);

let connections = []

io.on('connect',(socket) => {
   connections.push(socket);
   console.log(`${socket.id} has connected`);

   socket.on('draw',(data) => { 
      connections.forEach(con => {
         if(con.id !== socket.id){
            con.emit('ondraw', {x : data.x,y : data.y});
         }
      })
   });

   socket.on('down', (data) => {
      // 클라이언트가 서버로 down 이벤트를 보냈을 때 호출
      // window.onmousedown 의  io.emit('down',{x ,y }) 를 실행하면 이 코드가 실행.
      connections.forEach(con => {
         if(con.id !== socket.id){ // socket.id는  io.emit을 실행한 클라 아이디?
            con.emit('ondown',{x:data.x , y:data.y});
         }
         // 여기서 con도 socket이지 io가 아님 socket.emit임
         // 서버에서 emit하면 해당하는 이벤트리스너와 동일한 이름의
         // 리스너를 클라에서 io.on으로 작성해줘야 상호작용 가능.

         // 내가 이전에 만들어뒀던 채팅웹과 방식이 다름 그 차이는
         // socket.emit(): 메시지를 현재 클라이언트에게만 보냄.
         // 그래서 for문을 돌려서 다 보내는데 socket.id(현재 나)랑 같은거만 빼고 보냄
        // socket.broadcast.emit() 현재(자기 자신을 제외한 소켓을) 제외한 모든 다른 클라이언트에게 메시지를 보냄
         // 그래서 결국 둘은 같은거임
   });
   });

   socket.on('disconnect',(reason) =>{
      console.log(`${socket.id} is disconnected`)
      connections = connections.filter(con => con.id !== socket.id);
   } );
});
app.use(express.static('public'));

let PORT = process.env.PORT || 8080;

httpServer.listen(PORT, () => console.log(`Server started on port ${PORT}`));

/*
io.on 
클라가 서버로부터 이벤트를 받음 (전체클라가 받음)
클라측에서 그걸 받았을때의 로직을 클라에서 작성해야함
io.on('connection') , io.on('connect')는 서버에 새로운 클라가 연결될때마다 호출
그래서, connection 이벤트가 발생할때마다 해당 클라의 socket 객체가 전달됨.

socket.on
특정 클라에대한 이벤트 리스너
클라가 서버에 연결되면 각 클라는 고유한 socket 객체를 가지며
이 객체에 발생하는 이벤트를 처리하기 위해 사용
이벤트 이름을 임의로 설정할 수 있음. 그래서 down이나 draw같은 이름을 정의가능
그러면 클라에서도 이 이름에 맞게 이벤트이름을 정의해야 상호작용 가능함!

io.emit
클라측에서 서버로 메세지를 방송하는 메서드

socket.emit
서버측에서 특정 클라에게만 메세지를 보내는 메서드

socket.on('disconnect')나 io.on('connect')의 disconnect, connect는 고정된 이벤트이름임.
개발자가 임의로 바꿀 수 없다.

*/ 