import {useEffect} from "react";

function DisplayUser(props){

    return (
        <div>
            {Object.keys(props.user)
                .filter((key) => !["id"].includes(key))
                .map((key, index) => <div key={index}> {key}: {props.user[key]} </div>)}
        </div>
    )
}

export default DisplayUser;