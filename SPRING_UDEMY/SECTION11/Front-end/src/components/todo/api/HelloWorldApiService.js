import axios from 'axios'

// export retrieveHelloWorldBean(){
//     return 
// }

const apiClient = axios.create(
    {
        baseURL :'http://localhost:8080'
    }
)

export const retrieveHelloWorldBean = 
    () => apiClient.get('/hello-world-bean')

export const retrieveHelloWorldPathVariable = 
    (username) => apiClient.get(`/hello-world/path-variable/${username}`)