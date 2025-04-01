import { createContext ,useContext, useState } from "react";

// 로그인 세션을 유지하고 공유하는 방법에 대해 알아보자 

// 1: 컨텍스트를 생성한다
export const AuthContext = createContext()
export const useAuth = () => useContext(AuthContext)


// 2: 만들어진 컨텍스트를 다른 컴포넌트와 공유해보자

// 다른 컴포넌트에 컨텍스트를 제공하는 함수..
export default function AuthProvider({children}){ 
    // children : AuthProvider 모든 자식은 children에 할당됨
    // 컨텍스트에 state 추가하기
    const [number,setNumber] = useState(0);
    const [isAuthenticated,setAuthenticated] = useState(false);
   // const valueToBeShared = {number, isAuthenticated,setAuthenticated}

    function login(username,password){
        if(username ==='in28minutes' && password === 'dummy'){
            setAuthenticated(true)
            return true
        }else{
            setAuthenticated(false)
           return false
        }
    }

    function logout(){
        setAuthenticated(false)
    }
    return (
        <AuthContext.Provider value={{isAuthenticated,login,logout}}>
            {children}
        </AuthContext.Provider>
    )
}