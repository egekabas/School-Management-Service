import {useEffect, useState} from "react";
import {Link} from "react-router-dom";


function Logout() {

    const [loggedOut, setLoggedOut] = useState(false)

    useEffect(() => {
        fetch("/logout",
            {
                method: "POST",
                headers: {"Access-Control-Allow-Origin": "*"}
            }).then((response) => {setLoggedOut(true);})
    },[])

    return (
        <div>
            <header>{loggedOut ? "Successfully logged out" : "Logging out"}</header>
            <div>
                {loggedOut && <Link to = "/login"> Go back to login</Link>}
            </div>
        </div>
    )
}

export default Logout;