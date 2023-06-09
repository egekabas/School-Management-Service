import {useState} from "react";

function ChangePassword() {

    const [message, setMessage] = useState("");

    const handleSubmit = event => {
        event.preventDefault();
        if(event.target.newPassword.value === event.target.confirmNewPassword.value) {
            const body = JSON.stringify(
                {oldPassword: event.target.oldPassword.value, newPassword: event.target.newPassword.value});
            fetch('users/changePassword', {method: 'POST', headers: {'Content-Type': 'application/json'}, body: body})
                .then(response => response.text()
                    .then(message => setMessage(message)));
        } else{
            setMessage("New password confirmation doesnt match");
        }
    }

    return (
        <div>
            <div className="form">
                <form onSubmit={handleSubmit}>
                    <div className="input-container">
                        <label>Old Password </label>
                        <input type="password" name="oldPassword" required />
                    </div>
                    <div className="input-container">
                        <label>New Password </label>
                        <input type="password" name="newPassword" required />
                    </div>
                    <div className="input-container">
                        <label>Confirm New Password </label>
                        <input type="password" name="confirmNewPassword" required />
                    </div>
                    <div className="button-container">
                        <input type="submit" value = "Change Password"/>
                    </div>
                </form>
            </div>
            <div>{message}</div>
        </div>
    )
}

export default ChangePassword;