import { useContext } from "react";
import { useState } from "react";
import { createContext } from "react";

const LoginStateContext = createContext();

export const LoginStateProvider =({children})=>{

    /////???????????
    let accessToken = sessionStorage.getItem("AccessToken");
    let refreshToken = localStorage.getItem("RefreshToken"); 


    //true: login false:logout 상태 
    const [isLogin, setIsLogin]= useState(accessToken != null && refreshToken != null)

    function setLogin(){
        setIsLogin(true)
    }

    const setLogout =()=>{
        setIsLogin(flase)
    }

    return(
        <>
            <LoginStateContext.Provider value={{isLogin, setLogin, setLogout}}>
                {children}

            </LoginStateContext.Provider>
        
        </>
    )

}


export const useLoginStateContext=()=>{
    const loginStateContext = useContext(LoginStateContext)

    if(!loginStateContext){
        throw new Error("error")
    }

    return loginStateContext;
}