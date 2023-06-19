import React, {useEffect, useState} from "react";
import {Menu, MenuItem, Sidebar} from "react-pro-sidebar";
import {Link, Navigate, Route, Routes} from "react-router-dom";
import * as PropTypes from "prop-types";
import SubjectDelete from "./SubjectDelete";
import TaManager from "./TaManager";
import Assignments from "./Assignments";
import Students from "./Students";

function SubjectDetails(props) {
    return null;
}

SubjectDetails.propTypes = {children: PropTypes.node};

function SubjectManager(props){

    const user = props.user;
    const id = props.id;

    const [subject, setSubject] = useState(false);

    useEffect(() => {
        if(subject === false) {
            fetch(`/getSubjectDeep/${id}`).then(response => response.json()).catch(() => window.alert("Could not retrieve subject details"))
                .then(subject => {
                    setSubject(subject);
                });
        }
    }, [subject]);


    return (
        <div>
            { subject && <div>
                <div className="sidebar">
                    <Sidebar>
                        <Menu>
                            <MenuItem component={<Link to=""/>}> Details </MenuItem>
                            <MenuItem component={<Link to="students"/>}> View Students </MenuItem>
                            <MenuItem component={<Link to="tas"/>}> Manage TAs </MenuItem>
                            <MenuItem component={<Link to="assignments"/>}> Assignments </MenuItem>
                            <MenuItem component={<Link to="delete"/>}> Delete Subject </MenuItem>
                        </Menu>
                    </Sidebar>
                </div>
                <div className="center-content">
                    <Routes>
                        <Route path='' element={<SubjectMain user={user} subject={subject} setSubject = {setSubject}/>}/>
                        <Route path='delete' element={<SubjectDelete user={user} subject={subject} setSubject = {setSubject}/>}/>
                        <Route path='tas' element={<TaManager user={user} subject={subject} setSubject = {setSubject}/>}/>
                        <Route path='assignments/*' element={<Assignments user={user} subject={subject} setSubject = {setSubject}/>}/>
                        <Route path='students' element={<Students user={user} subject={subject} setSubject = {setSubject}/>}/>
                        <Route path="*" element={<Navigate to="/" replace />} />
                    </Routes>
                </div>
            </div>}
        </div>
    )
}

function SubjectMain(props){
    const subject = props.subject;

    return (
        <div>
            <div>Name: {subject.name}</div>
            <div>Description: {subject.description}</div>
            <div>Lecturer: {subject.lecturer.firstName + ' ' + subject.lecturer.lastName}</div>
        </div>
    )
}

export default SubjectManager;