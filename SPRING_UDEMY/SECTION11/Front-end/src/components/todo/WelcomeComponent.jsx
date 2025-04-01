import { useParams,Link} from 'react-router-dom'
import axios from 'axios'
import {useState} from 'react'
import { retrieveHelloWorldBean, retrieveHelloWorldPathVariable } from './api/HelloWorldApiService'

export default function WelcomeComponent(){

    const {username} = useParams()

    const [message,setMessage] =useState(null)

    console.log(username)

    function callHelloWorldRestApi(){
        console.log('called')
        // axios를 이용해서 REST API를 호출해보자
        // axios.get('http://localhost:8080/hello-world').
        //     then((response) => successfulResponse(response))
        //     .catch((error) => errorResponse(error))
        //     .finally(() => console.log('cleanup'))
        // 백엔드에서 hello-world에서 "Hello World v2"를 반환함
        // 그러면 response는 백엔드의 return 결과값이라 그거에 따라
        // then : 성공하면 , catch : 실패하면 결과를 내보냄

        // retrieveHelloWorldBean()
        //     .then((response) => successfulResponse(response))
        //     .catch((error) => errorResponse(error))
        //     .finally(() => console.log('cleanup'))
        
        retrieveHelloWorldPathVariable('Ranga')
            .then((response) => successfulResponse(response))
            .catch((error) => errorResponse(error))
            .finally(() => console.log('cleanup'))
        
    }
    function successfulResponse(response){
        console.log(response);
        //setMessage(response.data)
        setMessage(response.data.message)
        // 백엔드에서 단순 문자열이 아닌 객체로 줄때
        // response.data.message로 
    }
    function errorResponse(error){
        console.log(error);
    }
    
    return (
        <div className="WelcomeComponent">
            <h1>Welcome {username}</h1>
            <div>
                Manage Your todos-<Link to ="/todos">Go Here</Link>
            </div>
            <div>
                <button className="btn btn-success m-5" onClick={callHelloWorldRestApi}>
                    Call Hello World</button>
            </div>
            <div className='text-info'>{message}</div>

        </div>
    )
}


