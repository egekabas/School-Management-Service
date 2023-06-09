import {useEffect, useState} from "react";
import DisplayUser from "./DisplayUser";
import AddUser from "./AddUser";
import ListAll from "./ListAll";
import ChangePassword from "./ChangePassword";


function Home(props) {

    const isLoaded = props.isLoaded;
    const isAuthenticated = props.isAuthenticated;
    const user = props.user;
    const authorities = props.authorities;

    useEffect(() => {
        if(isLoaded && !isAuthenticated){
            window.location.replace('/login');
        }
    }, [isLoaded, isAuthenticated])

    return (
        <div>
            {isAuthenticated &&
                <div>
                    <DisplayUser user = {user}></DisplayUser>
                    <ChangePassword></ChangePassword>
                    <div>
                        {authorities.includes("ADMIN") &&
                            <div>
                                <div> Add User Menu </div>
                                <AddUser/>
                                <div> List All Users</div>
                                <ListAll user = {user}/>
                            </div>
                        }
                    </div>

                </div>
            }
        </div>
    );
}

export default Home;