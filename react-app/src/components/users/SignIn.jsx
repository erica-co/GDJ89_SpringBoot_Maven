import { Box, Button, TextField } from "@mui/material";
import { useRef } from "react";
import { useNavigate } from "react-router-dom";
import { useLoginStateContext } from "../../contexts/LoginStateContext";

export default function SignIn(){

    const loginState = useLoginStateContext("");

    const username = useRef("");
    const password = useRef("");

    const nac = useNavigate();

    function signInHandle(){
        // div -> div -> input
        //원하는 건 input에 있는 값을 가져오는 것 
        // div안에 있어서 username.current.value 값을 못 가져옴 -> inputRef로 가져오기 
        //console.log(username.current.value)
        //console.log(password.current.value)
        

        const params = new URLSearchParams();
        params.append("username", username.current.value) //append : 추가
        params.append("password", password.current.value)

        fetch(`http://localhost:81/user/login`,{
            method:"POST", //enctype ?
            body:params
        })
        .then(r=>{
            if(!r.ok){
                console.log(r.status)
                //console.log(r.text())
                throw new Error(r.status)

                //return r.json().then(Promise.reject.bind(Promise))
            }
            return r.headers

        })
        .then(r=>{
            //console.log(r.get('AccessToken'))
            console.log("success")
            //session storage
            window.sessionStorage.setItem("AccessToken", r.get('RefreshToken'))//key,value 형식
            //local storage
            window.localStorage.setItem("RefreshToken", r.get('RefreshToken'))

            loginState.setLogin()
            nav("/")
        })
        .catch(e=>{
            //console.log(e)
            if(e=='Error: 521'){
                alert('없는 사용자')
            }
            if(e=='Error: 522'){
                alert('비번 틀림')
            }
        })

    }

    return(
        <>
            <h3>SignIn Page</h3>
            
            <Box
                component="form"
                sx={{'&>:not(style':{m:1, width:'25ch'}}}
                noValidate
                autoComplete="off"
            >
            <TextField id="standard-basic" label="ID" variant="standard" inputRef={username} />   
            </Box>
            <Box
                component="form"
                sx={{'&>:not(style':{m:1, width:'25ch'}}}
                noValidate
                autoComplete="off" 
            >
            <TextField id="standard-basic" label="Password" variant="standard" inputRef={password} type="password" />   
            </Box>
            <br/>
            <Button variant="outlined" onClick={signInHandle}>Sign in</Button>
        
        </>
    )


}