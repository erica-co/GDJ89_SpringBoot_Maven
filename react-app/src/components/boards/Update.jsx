import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom"

export default function Update() {

    const loc = useLocation();
    const [result, setResult] = useState({});
    const navigate = useNavigate();

    //console.log(loc.state)
    
    useEffect(()=>{
        fetch(`http://localhost:81/notices/${loc.state.boardNum}`)
        .then(r=>r.json())
        .then(r=>{
            setResult(r)
        
        });

    },[])

    function update(e){
        e.preventDefault();
        //form에 있는 내용들을 전체 보내면 됨
        let f = new FormData(e.target)

        fetch(`http://localhost:81/notices`,{
            method:"PATCH",
            body:f
        }).then(r=>r.json)
        .then(r=>{
            navigate(`/notice/detail`, {state:{boardNum:loc.state.boardNum}})
        })

    }



    return (

        <>
            <h1>Update</h1>
            <form onSubmit={update}>
                <input type="hidden" name="boardNum" defaultValue={result.boardNum}></input>
                <div>
                    <input type="text" name="userName" defaultValue={result.userName}></input>
                    {/**dafultValue가 원래 내용을 웹에 띄움  */}
                </div>
                <div>
                    <input type="text" name="boardTitle" defaultValue={result.boardTitle}></input>
                </div>
                <div>
                    <textarea name="boardContents" id="" defaultValue={result.boardContents}></textarea>
                </div>
                <div>
                    <input type="file" name="attaches"></input>
                    <input type="file" name="attaches"></input>
                    <input type="file" name="attaches"></input>
                </div>
                <div>
                    <button type="submit">저장</button> 
                    {/**type 작성안하면 자동 submit, form 객체를 보냄  */}
                </div>
            </form>
        
        </>

    )
}