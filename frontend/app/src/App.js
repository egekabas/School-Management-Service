import './App.css';
import Login from "./pages/NoAuth/Login";
import User from "./pages/User/User";
import {BrowserRouter, Routes, Route, Navigate, Link,} from "react-router-dom";
import Logout from "./pages/NoAuth/Logout";
import NoRoleError from "./pages/NoAuth/NoRoleError";
import {useEffect, useState} from "react";
import Subject from "./pages/Subject/Subject";
async function authenticationCheck(){
    const response = await fetch("/getIfAuthenticated")
        .then(response => response.text());
    return response === "true";
}
async function getAuthorities(){
    return fetch("/getCurrentAuthorities")
        .then(response => response.json())
            .catch(() => window.alert("Could not load authorities"))
                .then(data => data
                    .map(object => object.authority)
                        .filter(authority => authority !== "ROLE_ANONYMOUS"));
}
async function getCurrentUser(){
    return fetch("/getCurrentUser").then(response => response.json()).catch(
        () => window.alert("Could not load current user")
    );
}
function App() {
    const [isAuthenticated, setIsAuthenticated] = useState("LOADING");
    const [user, setUser] = useState("LOADING");
    const [authorities, setAuthorities] = useState("LOADING");


    useEffect(() => {
        if(isAuthenticated === "LOADING") {
            authenticationCheck().then(isAuthenticated => {
                setIsAuthenticated(isAuthenticated);
            });
        }
    }, [isAuthenticated]);

    useEffect(() => {
        if(isAuthenticated !== "LOADING") {
            if(isAuthenticated) {
                getAuthorities().then(authorities => {
                    setAuthorities(authorities);
                });
                getCurrentUser().then(user => {
                    setUser(user);
                });
            } else {
                setAuthorities([]);
                setUser({});
            }
        }
    }, [isAuthenticated]);

    const isLoading = () => isAuthenticated === "LOADING" || user === "LOADING" || authorities === "LOADING";

    return (
        <div className="App">
            <BrowserRouter>
                {(!isLoading() && isAuthenticated) && <Link to='/'>
                    <button>GO TO HOME PAGE</button>
                </Link>}
                <Routes>
                    <Route path="/" element={ <Navigate to="/user" /> } />
                    <Route path='/login' element={<Login/>}/>
                    <Route path='/logout' element={<Logout setIsAuthenticated = {setIsAuthenticated}/>}/>
                    <Route path='/role-error' element={<NoRoleError/>}/>
                    <Route path='/user/*' element = {
                        isLoading() ? <div> LOADING </div> :
                        isAuthenticated ? <User isAuthenticated = {isAuthenticated} user= {user} authorities = {authorities}/> :
                        <Navigate to = '/login'/>
                    }/>
                    <Route path='/subject/:id/*' element = {
                        isLoading() ? <div> LOADING </div> :
                        isAuthenticated ? <Subject isAuthenticated = {isAuthenticated} user= {user} authorities = {authorities}/> :
                        <Navigate to = '/login'/>
                    }/>
                    <Route path="*" element={<Navigate to="/" replace />} />
                </Routes>
            </BrowserRouter>
        </div>
    );
}
export default App;