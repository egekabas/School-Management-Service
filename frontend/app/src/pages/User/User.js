import {useEffect, useState} from "react";
import DisplayUser from "./All/DisplayUser";
import AddUser from "./Admin/AddUser";
import ListUsers from "./Admin/ListUsers";
import ChangePassword from "./All/ChangePassword";
import AddSubject from "./Admin/AddSubject";
import {Menu, MenuItem, Sidebar} from "react-pro-sidebar";
import {Link, Navigate, NavLink, Route, Routes} from "react-router-dom";
import ListSubjects from "./All/ListSubjects";
import ListMySubjects from "./All/ListMySubjects";
import './User.css'


function User(props) {

    const isAuthenticated = props.isAuthenticated;
    const user = props.user;
    const authorities = props.authorities;


    return (
        <div>
            <div className="sidebar">
                <Sidebar>
                    <Menu>
                        <MenuItem component={<Link to = ""/>}> User Details</MenuItem>
                        <MenuItem component={<Link to = "change-password"/>}> Change Password</MenuItem>
                        <MenuItem component={<Link to = '/user/list-subjects'/>}>List All Subjects</MenuItem>
                        {user.role !== "ADMIN" && <MenuItem component={<Link to='/user/list-my-subjects'/>}>List My Subjects</MenuItem>}
                        <div>
                            {authorities.includes("ADMIN") &&
                                <div>

                                    <MenuItem component={<Link to = "/user/add-user"/>}> Add User</MenuItem>
                                    <MenuItem component={<Link to = "/user/add-subject"/>}> Add Subject</MenuItem>
                                    <MenuItem component={<Link to = "/user/list-users"/>}> List Users</MenuItem>

                                </div>
                            }
                        </div>
                        <MenuItem component={<Link to = '/logout'/>}>Logout</MenuItem>
                    </Menu>
                </Sidebar>
            </div>

            <div className='center-content'>
                <Routes>
                    <Route path='' element={<DisplayUser user = {user} authorities = {authorities}/>} />
                    <Route path='change-password' element={<ChangePassword user = {user} authorities = {authorities}/>}/>
                    <Route path='add-user' element={<AddUser user = {user} authorities = {authorities}/>}/>
                    <Route path='add-subject' element={<AddSubject user = {user} authorities = {authorities}/>}/>
                    <Route path='list-users' element={<ListUsers user = {user} authorities = {authorities}/>}/>
                    <Route path='list-subjects' element={<ListSubjects user = {user} authorities = {authorities}/>}/>
                    <Route path='list-my-subjects' element={<ListMySubjects user = {user} authorities = {authorities}/>}/>
                    <Route path="*" element={<Navigate to="/" replace />} />

                </Routes>
            </div>
        </div>
    );
}

export default User;