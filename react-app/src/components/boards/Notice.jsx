import { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";

function Notice(){

    // 상태관리 Hook 중 하나가 userState
    // state 값이 변하면 component는 재렌더링됨
    // state 값은 사라지지 않음 
    //[변수명, set변수명(변수를 담을 set함수)]= useState(초기값)

    const [list, setList] = useState({pager:"", list:[]});//초기값을 빈객체로 
    const [page, setPage] = useState(1);
    const [flag, setFlag] = useState(false);

    const search = useRef("");
    //useState()도 가능

    //useEffect
    //sideHook
    //useEffect(콜백함수, 의존성 배열)
    useEffect(()=>{
        console.log("userEffect")

        let params = new URLSearchParams();
        params.append('page', page)
        params.append('search',search.current.value)


        fetch(`http://localhost:81/notices?${params}`)
        .then(r=>r.json())
        .then(r=>{
            console.log("서버응답:", r)
            setList(r)
            
        });
    },[flag]);//[page]);


    //버튼 클릭
    function pageClick(e){
        console.log(e.target.getAttribute("data-page-num"))
        let p = e.target.getAttribute("data-page-num")
        setPage(p)
        setFlag(!flag) //flag면 변화가 없기 때문에 useEffect 실행 안됨 
    }

    //
    function makeNum(){
        const p = [];
        let b = <button onClick={pageClick} data-page-num={list.pager.start-1}>이전</button>
        p.push(b)
        //return에서는 for문 사용 불가능 
        for(let i=list.pager.start;i<=list.pager.end;i++){
            b = <button onClick={pageClick} data-page-num={i}>{i}</button> //span태그 안에 i를 넣는 element
            p.push(b)
        }
        b = <button onClick={pageClick} data-page-num={list.pager.end+1}>다음</button>
        p.push(b)

        return p;
    }

    function getSearch(){
        console.log(search.current.value)
        //출력 확인 -> useEffect가 또 실행돼야 새로운 list 재렌더링 
        setPage(0);
        setFlag(!flag);
        
    }


    return (
        <>
            <h1>Notice</h1>
            <div>
                <input type="text" ref={search}/><button onClick={getSearch}>search</button>
            </div>

            {   
                //map: 반복문 돌릴 때 사용
                list.list.map(l=>
                    <li key={l.boardNum}><Link to="/notice/detail" state={{boardNum:l.boardNum}}>{l.boardTitle}</Link></li>
                    //state : key , value 형식 여러개 보낼 수 있음 
                )

            }
            <hr></hr>
            {
                makeNum()
            }
            <br/>
            <br/>
            <Link to="/notice/add">글쓰기</Link>
            

        </>
    )

}

export default Notice;