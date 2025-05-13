import { Route, Router, Routes } from "react-router-dom";
import Home from "../components/home";
import Notice from "../components/boards/Notice";
import Qna from "../components/boards/Qna";
import Join from "../components/users/Join";

export default function AppRoutes() {

    return(

        <Routes>
            <Route path="/" element={<Home />}></Route>
            <Route path="/notice/list" element={<Notice />}></Route>
            <Route path="/qna/list" element={<Qna />}></Route>
            <Route path="/user/join" element={<Join />}></Route>

        </Routes>

    )

}