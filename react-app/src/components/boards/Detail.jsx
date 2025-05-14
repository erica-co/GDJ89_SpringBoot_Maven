//detail 페이지

import { useEffect, useRef, useState } from "react";
import { Link, useLocation, useNavigate, useParams, useSearchParams } from "react-router-dom"

export default function Detail() {
    // 파라미터 : URL/파라미터값/파라미터값 -> useParam 레스트풀형식으로 가져오기 
    // 쿼리스트링 : URL?이름=값&이름=값 -> useSearchParams
    // state : <Link to="" state={키:밸류}> 형식으로도 가져올 수 있음 -> useLocation 


    //1. useParam (파라미터 데이터를 가져올 때)
    //const 변수명 = useParams();
    //const 변수명 = useParams().파라미터명;
    
    //const num = useParams();

    //console.log("boardNum : ", num.boardNum)

    //2. useSearchParams
    //const [searchParams, setSearchparams] = useSearchParams();
    
    //console.log("search : ", searchParams)
    //console.log(searchParams.get("boardNum"))

    //3. useLocation
    // hash : 주소의 #문자열 뒤의 값
    // pathname : 현재 주소 경로
    // search : ?를 포함한 쿼리스트링
    // state : 페이지 이동시 임의로 넣을 수 있는 상태 값 
    // key : location 객체의 고유한 값 , 페이지가 변경될 때마다 고유의 값이 생성됨 

    //4. useNavagate(url, option)
    // state : 
    // replace : boolean : 뒤로가기 방지(true)
    // relative : route -> 절대경로, path-> 상대경로 , 기본값:route
    // /notice/list
    // navigate("detail", {relative:"route"}) => /notice/detail 
    // navigate("detail", {relative:"path"}) => /notice/list/detail 


    const p = useLocation();
    // console.log(p.state.boardNum);
    const [num, setNum] = useState(p);
    const [result, setResult] = useState({});
    const navigate = useNavigate();

    useEffect(()=>{
        fetch(`http://localhost:81/notices/${p.state.boardNum}`)
        .then(r=>r.json())
        .then(r=>{
            //console.log("서버응답:", r)
            setResult(r)
        
        });

    },[])

    function remove() {

        fetch(`http://localhost:81/notices/${p.state.boardNum}`,{
            method:"DELETE"
        })
        .then(r=>r.json())
        .then(r=>{
            //console.log("서버응답:", r)
            navigate("/notice/list")  
        
        });

        
    }




    return(
        <>
            <h1>Detail</h1>
            <h3>{result.boardTitle}</h3>

            <Link to="/notice/update" state={{boardNum:result.boardNum}}>Update</Link>
            <button onClick={remove}>Delete</button>
            
        </>



    )
}