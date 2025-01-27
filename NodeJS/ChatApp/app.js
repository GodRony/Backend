const express = require('express')
const path = require('path')
const app = express()
const PORT = process.env.PORT || 4000
const server = app.listen(PORT,() => console.log(`char server on port ${PORT}`))

const io = require('socket.io')(server)

app.use(express.static(path.join(__dirname,'public')))

let socketsConnected = new Set()

io.on('connection',onConnected);

function onConnected(socket){
    console.log(socket.id)
    socketsConnected.add(socket.id)

    io.emit('clients-total',socketsConnected.size)

    socket.on('disconnect',()=>{
        console.log('Socket disconnected',socket.id)
        socketsConnected.delete(socket.id)
        io.emit('clients-total',socketsConnected.size)
    })

    socket.on('message', (data) =>{
        console.log(data)
        socket.broadcast.emit('chat-message',data)
        //모든 클라이언트에게 메시지를 전송하는 방식
    })

    //클라(main.js)에서 socket.emit('message', data)를 실행하면 위 코드가 실행됨

    socket.on('feedback',(data) => {
        socket.broadcast.emit('feedback',data)
    })
}
// public 폴더의 JavaScript 코드가 io.emit으로 보낸 데이터를 받는 주체임.
// io.emit은 서버에 연결된 모든 클라이언트에게 데이터를 보냅니다. 
// 따라서, public 폴더 안에 JavaScript 파일이 여러 개 있더라도, 중요한 건 해당 JavaScript 파일들이 실행되고 있는 클라이언트입니다.
// JavaScript 파일마다 socket.on 이벤트 리스너를 설정했다면, 동일한 데이터를 각각의 리스너가 받음!