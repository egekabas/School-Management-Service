import {useEffect, useState} from "react";

function Login(){
    const [errorMessages, setErrorMessages] = useState({});
    const [isSubmitted, setIsSubmitted] = useState(false);
    const  [result, setResult] = useState("");


    // Generate JSX code for error message
    const renderErrorMessage = (name) =>
        name === errorMessages.name && (
            <div className="error">{errorMessages.message}</div>
        );

    const handleSubmit = (event) => {
        event.preventDefault();
        const body = JSON.stringify({username: event.target.username.value, password: event.target.password.value});
        fetch("login", {method: 'POST', headers: {'Content-Type':'application/json'}, body: body})
            .then(response => response.text())
                .then(loginResult => {
                    if(loginResult === "Login Successful"){
                        setResult("Login Successful");
                        window.location.replace("/");
                    } else{
                        setResult("Incorrect username or password");
                    }
                }
        )
    }

    // JSX code for login form
    return (
        <div>
            <div className="form">
                <form onSubmit={handleSubmit}>
                    <div className="input-container">
                        <label>Username </label>
                        <input type="text" name="username" required />
                        {renderErrorMessage("username")}
                    </div>
                    <div className="input-container">
                        <label>Password </label>
                        <input type="password" name="password" required />
                        {renderErrorMessage("password")}
                    </div>
                    <div className="button-container">
                        <input type="submit" />
                    </div>
                </form>
            </div>
            <p>{result}</p>
        </div>
    );
}

export default Login