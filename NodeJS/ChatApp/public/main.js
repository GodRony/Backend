const socket = io()

const clientsTotal = document.getElementById('client-total')

const messageContainer = document.getElementById('message-container')
const nameInput = document.getElementById('name-input')
const messageForm = document.getElementById('message-form')
const messageInput = document.getElementById('message-input')
// document.getElementById나 document.querySelector 같은 메서드로 
// 요소를 찾는 대상은 HTML 파일의 DOM(Document Object Model) 구조임

const messageTone = new Audio('/message-tone.mp3')

messageForm.addEventListener('submit', (e) => {
  e.preventDefault()
  sendMessage()
})

socket.on('clients-total', (data) => {
  clientsTotal.innerText = `Total Clients: ${data}`
})
// 이거는 app.js 파일에서 받는거임.
// io.emit('clients-total',socketsConnected.size) 이 코드에서 받는교.
// socket.on('event-name', callback) : 이벤트 event-name이 발생했을 때 callback 함수를 실행

function sendMessage() {
  if (messageInput.value === '') return
  // console.log(messageInput.value)
  const data = {
    //html로부터 input타입을 id로 위에서 받았고, value로 접근함 (input타입은 이게 가능)
    name: nameInput.value, 
    message: messageInput.value,
    dateTime: new Date(),
  }
  socket.emit('message', data)
  //socket.emit('message', data)는 클라측(main.js)에서 서버측(app.js)으로 데이터를 보냄
  addMessageToUI(true, data)
  messageInput.value = ''
}

socket.on('chat-message', (data) => {
  // console.log(data)
  messageTone.play()
  addMessageToUI(false, data)
})
// 서버로부터 받는 코드는 socket.on임
// 반면 클라가 서버로 보내는 코드가 socket.emit임 
// 무슨 함수를 실행할건지는 ''로 표시됨!

function addMessageToUI(isOwnMessage, data) {
  clearFeedback()
  const element = `
      <li class="${isOwnMessage ? 'message-right' : 'message-left'}">
          <p class="message">
            ${data.message}
            <span>${data.name} ● ${moment(data.dateTime).fromNow()}</span>
          </p>
        </li>
        `

  messageContainer.innerHTML += element
  scrollToBottom()
}

function scrollToBottom() {
  messageContainer.scrollTo(0, messageContainer.scrollHeight)
}

messageInput.addEventListener('focus', (e) => {
  socket.emit('feedback', {
    feedback: `✍️ ${nameInput.value} is typing a message`,
  })
})

messageInput.addEventListener('keypress', (e) => {
  socket.emit('feedback', {
    feedback: `✍️ ${nameInput.value} is typing a message`,
  })
})
messageInput.addEventListener('blur', (e) => {
  socket.emit('feedback', {
    feedback: '',
  })
})

socket.on('feedback', (data) => {
  clearFeedback()
  const element = `
        <li class="message-feedback">
          <p class="feedback" id="feedback">${data.feedback}</p>
        </li>
  `
  messageContainer.innerHTML += element
})

function clearFeedback() {
  document.querySelectorAll('li.message-feedback').forEach((element) => {
    element.parentNode.removeChild(element)
  })
  // querySelectorAll은 주어진 선택자에 맞는 모든 요소를 찾아 노드 리스트를 반환
  // 이 부분은 모든 li.message-feedback 요소를 찾음.
  // 부모자체를 없애서 하위도 모두 없애버림
}