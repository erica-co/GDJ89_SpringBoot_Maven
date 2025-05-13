import { useEffect, useState } from "react";

function Notice(){

    // 상태관리 Hook 중 하나가 userState
    // state 값이 변하면 component는 재렌더링됨
    // state 값은 사라지지 않음 
    //[변수명, set변수명(변수를 담을 set함수)]= useState(초기값)
    const [list, setList] = useState([]);
    //useState()도 가능

    //useEffect
    //sideHook
    //useEffect(콜백함수, 의존성 배열)
    useEffect(()=>{
        console.log("userEffect")
        fetch("http://localhost:81/notice/list")
        .then(r=>r.json())
        .then(r=>{
            console.log("서버응답:", r)
            setList(r)
        });
    },[]);
    //useEffect없이 fetch만 할 경우 list의 state가 변하기 때문에 재랜더링됨 -> 무한으로 요청됨
    //그래서 useEffect의 빈배열을 통해 한번 만 호출 


    return (
        <>
            <h1>Notice</h1>

            {   
                //map: 반복문 돌릴 때 사용
                list.map(l=>
                    <li key={l.boardNum}>{l.boardTitle}</li>
                )

            }

        </>
    )

}

export default Notice;