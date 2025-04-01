import { useState } from 'react'
import { useNavigate} from 'react-router-dom'
import { useAuth } from './security/AuthContext'

export default function LoginComponent(){

    const [username,setUsername] = useState('in28minutes')
    const [password,setPassword] = useState('')
    const [showErrorMessage,setShowErrorMessage] = useState(false)
    
    const navigate = useNavigate();

    const authContext = useAuth()

    function handleUsernameChange(event){
        setUsername(event.target.value)
    }

    function handlePasswordChange(event){
        setPassword(event.target.value)
    }

    function handleSubmit(){
        if(authContext.login(username,password)){
            navigate(`/welcome/${username}`)
        }else{
            setShowErrorMessage(true);
        }
    }

    return(
        <div className="Login">
            <h1>Time to Login!</h1>
            {showErrorMessage && <div className = "errorMessage"> Authenticated Failed </div>}
            {/* <ErrorMessageComponent/> */}
            <div className = "LoginForm"> 
                <div>
                    <label>User Name</label>
                    <input type = "text" name="username" 
                    value = {username} onChange={handleUsernameChange}></input>
                </div>
                <div>
                    <label>Password</label>
                    <input type = "password" name="password"
                    value = {password} onChange={handlePasswordChange}></input>
                </div>
                <div>
                    <button type="button" name="login" onClick={handleSubmit}>login</button>
                </div>
                
            </div>
            Login Component 
        </div>
    )
}
