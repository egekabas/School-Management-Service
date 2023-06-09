import logo from './logo.svg';
import './App.css';
import Login from "./components/Login";
import Home from "./components/Home";
import {BrowserRouter, Routes, Route,} from "react-router-dom";
import Logout from "./components/Logout";
import NoRoleError from "./components/NoRoleError";
import AddUser from "./components/AddUser";
import {useEffect, useState} from "react";


async function authenticationCheck(){
    const response = await fetch("users/getIfAuthenticated")
        .then(response => response.text()).catch(() => "false");
    return response === "true";
}
async function getAuthorities(){
    return fetch("users/getCurrentAuthorities")
        .then(response => response.json())
        .then(data => data
            .map(object => object.authority)
                .filter(authority => authority !== "ROLE_ANONYMOUS"));
}

async function getCurrentUser(){
    return fetch("users/getCurrentUser").then(response => response.json());
}

function App() {

    const [isLoaded, setIsLoaded] = useState(false);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [user, setUser] = useState({});
    const [authorities, setAuthorities] = useState([]);


    useEffect(() => {
        authenticationCheck().then(isAuthenticated => {
            setIsLoaded(true);
            setIsAuthenticated(isAuthenticated);
        })
    }, []);

    useEffect(() => {
        if(isLoaded && isAuthenticated) {
            getAuthorities().then(authorities => {
                setAuthorities(authorities);
            });
        }
    }, [isLoaded, isAuthenticated]);

    useEffect(() => {
        if(isLoaded && isAuthenticated) {
            getCurrentUser().then(user => {
                setUser(user);
            });
        }
    }, [isLoaded, isAuthenticated]);



    return (
        <div className="App">
            <BrowserRouter>
                <Routes>
                    <Route path='/login' element={<Login/>}/>
                    <Route path='/logout' element={<Logout/>}/>
                    <Route path='/' element={
                        <Home isLoaded = {isLoaded} isAuthenticated = {isAuthenticated}
                              user = {user} authorities = {authorities}
                        />
                    }/>
                    <Route path='/role-error' element={<NoRoleError/>}/>

                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
