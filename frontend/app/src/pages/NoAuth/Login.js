import {useEffect, useState} from "react";
import './Login.css'

function Login(){
    const [isSubmitted, setIsSubmitted] = useState(false);
    const  [result, setResult] = useState("");



    const handleSubmit = (event) => {
        event.preventDefault();
        const body = JSON.stringify({username: event.target.username.value, password: event.target.password.value});
        fetch("/login", {method: 'POST', headers: {'Content-Type':'application/json'}, body: body})
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
        <div className="login-page">
            <div>
                <form onSubmit={handleSubmit} className="login-form">
                    <div>
                        <label>Username </label>
                        <input type="text" name="username" required />
                    </div>
                    <div>
                        <label>Password </label>
                        <input type="password" name="password" required />
                    </div>
                    <div>
                        <input type="submit" value = "Login"/>
                    </div>
                </form>
            </div>
            <p>{result}</p>
        </div>
    );
}

export default Login