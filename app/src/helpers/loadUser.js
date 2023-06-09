
function getCurrentUser(){
    return fetch("users/getCurrentUser").then(response => response.json());
}
export function loadUser(safe, setUser){
    if(safe) {
        getCurrentUser().then((user) => {
            setUser(user);
        });
    }
}

export default loadUser;