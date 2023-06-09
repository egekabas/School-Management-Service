async function getAuthorities(){
    const response = await fetch("users/getCurrentAuthorities").then(response => response.json());
    return response.map(
        object => object.authority
    ).filter(authority => authority !== "ROLE_ANONYMOUS");
}

function loadAuthorities(safe, setAuthorities){
    if(safe) {
        getAuthorities().then(authorities => {
            setAuthorities(authorities);
        });
    }
}

export default loadAuthorities;