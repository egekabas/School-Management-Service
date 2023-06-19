import {useEffect, useState} from "react";

function ChangePassword(props) {

    const [message, setMessage] = useState("");

    const handleSubmit = event => {
        event.preventDefault();
        if(event.target.newPassword.value === event.target.confirmNewPassword.value) {
            const body = JSON.stringify(
                {oldPassword: event.target.oldPassword.value, newPassword: event.target.newPassword.value});
            fetch('/changePassword', {method: 'POST', headers: {'Content-Type': 'application/json'}, body: body})
                .then(response => response.text()
                    .then(message => setMessage(message)));
        } else{
            setMessage("New password confirmation doesnt match");
        }
        event.target.oldPassword.value = "";
        event.target.newPassword.value = "";
        event.target.confirmNewPassword.value = "";
    }

    return (
        <div>
            <div>
                <form onSubmit={handleSubmit}>
                    <div>
                        <div>Old Password </div>
                        <input type="password" name="oldPassword" required />
                    </div>
                    <div>
                        <div>New Password </div>
                        <input type="password" name="newPassword" required />
                    </div>
                    <div>
                        <div>Confirm New Password </div>
                        <input type="password" name="confirmNewPassword" required />
                    </div>
                    <div>
                        <input type="submit" value = "Change Password"/>
                    </div>
                </form>
            </div>
            <div>{message}</div>
        </div>
    )
}

export default ChangePassword;