import {useEffect, useState} from "react";
import forceAuthentication from "../helpers/forceAuthentication";
import loadUser from "../helpers/loadUser";
import loadAuthorities from "../helpers/loadAuthorities";


function Home(props) {


    const [loaded, setLoaded] = useState(false);
    const [user, setUser] = useState({});
    const [authorities, setAuthorities] = useState([]);

    useEffect(() => forceAuthentication(setLoaded), []);

    useEffect( () => loadUser(loaded, setUser), [loaded]);

    useEffect(() => loadAuthorities(loaded, setAuthorities), [loaded]);

    return (
        <div>
            {loaded &&
                <div>
                    <div>
                        {Object.keys(user)
                            .filter((key) => key !== "password" && key !== "id")
                            .map((key, index) => <div key={index}> {key}: {user[key]} </div>)}
                    </div>
                    <div>
                        {authorities.includes("ADMIN") && <div>
                            Create New User
                        </div>}
                    </div>
                </div>
            }
        </div>
    );
}

export default Home;