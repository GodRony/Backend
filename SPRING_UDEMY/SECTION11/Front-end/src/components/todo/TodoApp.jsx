import { BrowserRouter,Routes,Route,Navigate} from 'react-router-dom'
import './TodoApp.css'
import LogoutComponent from './LogoutComponent'
import FooterComponent from './FooterComponent'
import HeaderComponent from './HeaderComponent'
import ListTodosComponent from './ListTodosComponent'
import ErrorComponent from './ErrorComponent'
import WelcomeComponent from './WelcomeComponent'
import LoginComponent from './LoginComponent'
import AuthProvider,{useAuth} from './security/AuthContext'

function AuthenticatedRoute({children}){
    const authContext = useAuth()
    if(authContext.isAuthenticated)
        return children
    return <Navigate to="/"/>
}

export default function TodoApp(){
    return(
        <div className="TodoApp">
            <AuthProvider>
                <BrowserRouter>
                <HeaderComponent/>

                    <Routes>
                        <Route path='/' element={<LoginComponent/>}/>
                        <Route path='/login' element={<LoginComponent/>}/>
                     
                        <Route path='/welcome/:username' element={
                            <AuthenticatedRoute>
                                <WelcomeComponent/>
                            </AuthenticatedRoute>
                            }/>
                     
                        <Route path='/todos' element={
                            <AuthenticatedRoute>
                                <ListTodosComponent/>
                            </AuthenticatedRoute>
                            
                            }/>
                     
                        <Route path='/logout' element={<LogoutComponent/>}/>
                     
                        <Route path='/*' element={<ErrorComponent/>}/>
                        
                    </Routes>
                    <FooterComponent/>

                </BrowserRouter>
            </AuthProvider>
        </div>
    )
}



/** 
### 순회방법 #### 
1. todos.forEach( e => console.log(e) )
2. todos.map(e => e.id) // 각각의 요소 뽑는게 가능

jsx에서는!
동적으로 할거면 자바스크립트지? 이거는 {} 괄호 열어줘야해
그리고 jsx 표현으로는 ()을 열어줘

### 그외 알아가면 좋을 point ###
a href 를 사용하면 전체 페이지가 새로고침되니까 (이 경우 네트워크 활동이 생김)
Link to 를 써라 얘는 특정 컴포넌트만 refresh해줌

 */