async function authenticationCheck(){
    const response = await fetch("users/getIfAuthenticated")
                                .then(response => response.text()).catch(() => "false");
    return response === "true";
}

function forceAuthentication(setLoaded){
    authenticationCheck().then(isAuthenticated => {
        if(!isAuthenticated){
            window.location.replace("/login");
        } else {
            setLoaded(true);
        }
    });
}

export default forceAuthentication;