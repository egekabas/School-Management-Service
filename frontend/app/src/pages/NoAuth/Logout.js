import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import './Logout.css'

function Logout(props) {

    const [loggedOut, setLoggedOut] = useState(false)

    useEffect(() => {
        fetch("/logout",
            {
                method: "POST",
                headers: {"Access-Control-Allow-Origin": "*"}
            }).then(response => response.text()).then(response => {
                setLoggedOut(true);
                props.setIsAuthenticated("LOADING");
            }).catch(() => window.location.reload());
    },[]);

    return (
        <div className="logout-page">
            <header>{loggedOut ? "Successfully logged out" : "Logging out"}</header>
            <div>
                {loggedOut && <Link to = "/login"> Go back to login</Link>}
            </div>
        </div>
    )
}

export default Logout;