function DisplayUser(props){
    return (
        <div>
            {Object.keys(props.user)
                .filter((key) => key !== "password" && key !== "id")
                .map((key, index) => <div key={index}> {key}: {props.user[key]} </div>)}
        </div>
    )
}

export default DisplayUser;