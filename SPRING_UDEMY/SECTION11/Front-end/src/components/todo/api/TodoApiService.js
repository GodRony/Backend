import axios from 'axios'

// export retrieveHelloWorldBean(){
//     return 
// }

const apiClient = axios.create(
    {
        baseURL :'http://localhost:8080'
    }
)


export const retrieveAllTodosForUsername = 
    (username) => apiClient.get(`/users/${username}/todos`)
    //http://localhost:8080/users/in28minutes/todos
    // 백엔드에서 Todos를 받는 작업