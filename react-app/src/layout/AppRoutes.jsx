import { Route, Router, Routes } from "react-router-dom";
import Home from "../components/home";
import Notice from "../components/boards/Notice";
import Detail from "../components/boards/Detail";
import Qna from "../components/boards/Qna";
import Join from "../components/users/Join";
import Add from "../components/boards/add";
import Update from "../components/boards/Update";




export default function AppRoutes() {

    return(

        <Routes>
            <Route path="/" element={<Home />}></Route>
            <Route path="/notice/">
                <Route path="list" element={<Notice />}></Route>
                {/*<Route path="detail/:boardNum" element={<Detail />}></Route> useParam 사용시 */} 
                <Route path="detail" element={<Detail />}></Route>
                <Route path="add" element={<Add/>}></Route>
                <Route path="update" element={<Update/>}></Route>
            </Route>
            <Route path="/qna/list" element={<Qna />}></Route>
            <Route path="/user/join" element={<Join />}></Route>
            

        </Routes>

    )

}