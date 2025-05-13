import { Link } from "react-router-dom";

export default function Header() {

    return (

        <>
            <h3>Header</h3>
            <div>
                <Link to="/notice/list">Notice</Link><br/>
                <Link to="/qna/list">Qna</Link><br/>
                <Link to="/user/join">Join</Link><br/>
                <Link to="/">Home</Link>
            </div>
            
            
        
        </>
    )

}