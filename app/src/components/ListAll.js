import {useEffect, useState} from "react";
import DisplayUser from "./DisplayUser";
import {Button} from "reactstrap";

function ListAll(props) {
    const curUser = props.user;

    const [userList, setUserList] = useState([])

    useEffect(() => {
        fetch('users/getAll')
            .then(response => response.json().
                then(users => setUserList(users)));
    });

    const deleteById = id => {
        fetch(`users/delete/${id}`, {method: 'DELETE', headers: {}});
    }


    return (
        <div>
            {userList.map(user =>
                <div key = {user.id}>

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

export default ListAll;