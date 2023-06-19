import {useEffect, useState} from "react";
import DisplayUser from "../All/DisplayUser";
import {Button} from "reactstrap";
import './ListUsers.css'
function ListUsers(props) {

    useEffect(() => {
        if(!props.authorities.includes("ADMIN")){
            window.location.replace("/role-error?role=ADMIN")
        }
    }, [props.authorities]);



    const curUser = props.user;
    const [userList, setUserList] = useState([])

    const loadUsers = () => {
        fetch('/getAllUsers')
            .then(response => response.json().
                then(users => setUserList(users))).catch(() => window.alert("Could not load users"));
    }

    useEffect(loadUsers, []);

    const deleteById = async id => {
        const subjects = await fetch(`/getSubjectsTaught/${id}`).then(response => response.json());

        console.log(subjects);

        if(subjects.length === 0 || window.confirm(
            "Deleting this user would also delete the following classes:\n" +
            subjects.reduce((str, subject) => str + subject.name + "\n", "")
        )) {
            const message = await fetch(`/deleteUser/${id}`, {method: 'DELETE', headers: {}}).then(response => response.text());
            if(message !== "Deleted Successfully"){
                window.alert(message);
            }
        }
        loadUsers();
    }


    return (
        <div>
            {userList.map(user =>
                <div key = {user.id} className= 'user-container'>

                    <div>User {user.id}</div>


                    <DisplayUser key = {user.id} user = {user} />

                    <div>{curUser.id !== user.id &&
                            <Button onClick={() => deleteById(user.id)} key = {user.id}>
                                Delete
                            </Button>}
                    </div>
                </div>
            )}
        </div>
    );
}

export default ListUsers;