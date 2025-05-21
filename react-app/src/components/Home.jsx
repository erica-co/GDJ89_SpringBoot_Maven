
import { Button } from "@mui/material";
import { useLoginStateContext } from "../contexts/LoginStateContext";

function Home() {

    const loginState = useLoginStateContext(); //islogin, setLogin, setLogout

    return (
        <>
            <h1>Home</h1>
            <Button variant="contained">Sample</Button>

            {
                loginState.isLogin?
            <h1>Login 성공</h1>
                :
            <h1>Login 전</h1>
            }
    
        </>
    )


}

export default Home;