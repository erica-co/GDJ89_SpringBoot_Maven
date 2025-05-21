import SockJS from "sockjs-client";

export default function Footer() {
    getSocket(); //useEffect 사용 가능 

    function getSocket(){
        console.log("socket 연결 시도")
        const socket = new SockJS("/ws/chat}",{
            
        })

        socket.onopen=function(){
            console.log("socket 연결 성공")
        }

        socket.onmessage=function(e){
            console.log("메세지 수신")
            console.log(e.data)
        }

        socket.onclose=function(){
            console.log("socket 연결 해제")
        }

        socket.onerror=function(){
            console.log("socket 오류")
        }

        function send(m) {
            socket.send(m);
        }
    }

    return(
        <>
            <hr></hr>
            <h3>Footer</h3>
        </>

    )

} 