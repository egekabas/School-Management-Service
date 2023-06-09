import {resolvePath} from "react-router-dom";
import {useEffect, useState} from "react";
import {Button} from "reactstrap";

function AddUser(props){

    // useEffect(() => {
    //     if(!props.authorities.includes("ADMIN")){
    //         window.location.replace("role-error?role=ADMIN")
    //     }
    // },[])

    const [createdPassword, setCreatedPassword] = useState(undefined);
    const handleClick = async (event) => {
        event.preventDefault();

        const body = JSON.stringify({
            role: event.target.role.value,
            firstName: event.target.firstName.value,
            lastName: event.target.lastName.value,
            email: event.target.email.value,
            username: event.target.username.value
        });
        const formResult = await fetch("users/add", {method: "POST", headers: {'Content-Type':'application/json'}, body: body}).then(
            response => response.text()
        );
        console.log(formResult);
        setCreatedPassword(formResult);
    };

    return ( <div>
            { createdPassword === undefined ?
                <div>
                    <div className="form">
                        <form onSubmit={handleClick}>
                            <div>
                                <label>Role </label>
                                <select name="role">
                                    <option value="ADMIN">Admin</option>
                                    <option value="LECTURER">Lecturer</option>
                                    <option value="TA">TA</option>
                                    <option value="STUDENT">Student</option>
                                </select>
                            </div>

                            <div className="input-container">
                                <label>First Name </label>
                                <input type="text" name="firstName" required/>
                            </div>
                            <div className="input-container">
                                <label>Last Name </label>
                                <input type="text" name="lastName" required/>
                            </div>
                            <div className="input-container">
                                <label>Email </label>
                                <input type="email" name="email" required/>
                            </div>
                            <div className="input-container">
                                <label>Username </label>
                                <input type="text" name="username" required/>
                            </div>

                            <div className="button-container">
                                <input type="submit" value = "Add User"/>
                            </div>
                        </form>
                    </div>
                </div>
                    :
                <div>

                    <div>
                        {`User created with randomly generated password ${createdPassword}`}
                    </div>

                    <Button variant = "contained" onClick={() => setCreatedPassword(undefined)} >
                        Add Another
                    </Button>
                </div>
            }
        </div>
    );
}

export default AddUser;