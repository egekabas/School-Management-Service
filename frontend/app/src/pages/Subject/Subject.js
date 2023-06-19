import {Link, Navigate, Route, Routes, useParams, useSearchParams} from "react-router-dom";
import {useEffect, useState} from "react";
import NoAccess from "./NoAccess";
import SubjectManager from "./SubjectManager/SubjectManager";
import {Button, Nav} from "reactstrap";
import StudentPage from "./StudentPage";
import TaPage from "./TaPage";


function Subject(props){
    const isAuthenticated = props.isAuthenticated;
    const user = props.user;
    const authorities = props.authorities;

    const {id} = useParams();
    const [hasAccess, setHasAccess] = useState(false);

    useEffect(() => {
        fetch(`/checkSubjectAccess/${id}`).then(response => response.text().then(result => {
            setHasAccess(result === "true");
            if(result !== "true" && result !== "false"){
                window.confirm("Could not find subject with id " + id);
                window.location.replace('/');
            }
        }));
    }, []);

    return (
        <div>
            {
                !isNaN(id) ?
                    (
                !hasAccess ? <NoAccess user = {props.user} id = {id}/> :
                    user.role === "STUDENT" ? <StudentPage user = {props.user} id = {id}/> :
                        user.role === "TA" ? <TaPage user = {props.user} id = {id}/> :
                            <SubjectManager user = {props.user} id = {id}/>)  : <Navigate to = '/'/>
            }
        </div>
    );
}

export default Subject;