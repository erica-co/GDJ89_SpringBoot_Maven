
// 작성자, 제목, 내용, 첨부파일 3개 고정 

import { useRef, useState } from "react"
import { useNavigate } from "react-router-dom";

export default function Add(){

    const navigate = useNavigate();

    //input 태그마다 설정해주기 : ref 
   const username = useRef();
   const title = useRef();
   const contents = useRef(); 

   const [datas, setDatas]=useState({
    userName:"",
    boardTitle:"",
    boardContents:""
   });

   function changeInput(e){
    //useState 내용이 변경이 되면 "" 안에 넣으려고 하는 거임
    //prevState : useState 객체를 그대로 가져와라 
        {/*setDatas((prevState)=>({
            ...prevState, //전개
            [e.target.name]:e.target.value
        })
        )*/}
   }

   function add(e){
        let f = new FormData(e.target) //e.target=<form>  
        //Formdata 아님!! 대소문자 구분 잘하기!
        fetch(`http://localhost:81/notices`,{
        method:"POST",
        headers:{

            },
            body:f
        }).then(r=>r.json())
            .then(r=>{
                console.log("서버응답:", r)
                navigate("/notice/list")
                
            })

   }

    function add2(){
        console.log(datas);
        //console.log(username.current.value)
        //console.log(title.current.value)
       // console.log(contents.current.value)

       //1. URLSearchParams 
        //let params = new URLSearchParams();
        //params.append("username",username.current.value)
        //params.append("boardTitle",title.current.value)
        //params.append("boardContents",contents.current.value)

        //2. formData 객체 이용 
        let d = new FormData();
        d.append("userName", "");

        //서버 요청 시 CORS 허용 spring Security 사용 

        fetch(`http://localhost:81/notices`,{
            method:"POST",
            headers:{

            },
            body:d //JSON.stringify(datas) //json 형식의 문자열을 파라미터로 보내고 싶을 때 -> 서버측에서 변환하는 코드 작성 필수 
        }).then(r=>r.json())
            .then(r=>{
                if(r>0){
                    //import , 객체 생성해줘야 사용 가능 
                    useNavigate
                }
                
            })


        

        
    }



    return (

        <>
            <h1>Add</h1>
            <form onSubmit={add}>
                <div>
                    <input type="text" name="userName" onChange={changeInput} ref={username}></input>
                </div>
                <div>
                    <input type="text" name="boardTitle" onChange={changeInput} ref={title}></input><br/>
                </div>
                <div>
                    <textarea name="boardContents" id="" onChange={changeInput} ref={contents}></textarea>
                </div>
                <div>
                    <input type="file" name="attaches"></input>
                    <input type="file" name="attaches"></input>
                    <input type="file" name="attaches"></input>
                </div>
                <div>
                    <button type="submit">작성</button> 
                    {/**type 작성안하면 자동 submit, form 객체를 보냄  */}
                </div>
            </form>

        </>
    )

}