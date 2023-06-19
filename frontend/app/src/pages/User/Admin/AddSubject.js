import {useEffect, useState} from "react";
import * as events from "events";


function AddSubject(props){
    useEffect(() => {
        if(!props.authorities.includes("ADMIN")){
            window.location.replace("/role-error?role=ADMIN")
        }
    }, [props.authorities]);



    const [lecturers, setLecturers] = useState([]);

    useEffect(
        () => {
            fetch('/getAllLecturers').then(response => {
               response.json().catch(() => window.alert("Could not retrieve lecturers"))
                   .then(lecturers => {
                    setLecturers(lecturers);
               });
            });
        }, []
    );

    const handleClick = async event =>{
        event.preventDefault();
        const body = JSON.stringify({
            name: event.target.name.value,
            description: event.target.description.value,
            lecturerUserName: event.target.lecturer.value
        });

        name: event.target.name.value = "";
        description: event.target.description.value = "";

        const formResult = await fetch("/addSubject", {method: "POST", headers: {'Content-Type':'application/json'}, body: body}).then(
            response => response.text().catch(() => "Something went wrong")
        );
        window.alert(formResult);
    }

    return (<div>
        <div>
            <form onSubmit={handleClick}>

                <div>
                    <label>Subject Name </label>
                    <input type="text" name="name" required/>
                </div>
                <div>
                    <label>Description </label>
                    <input type="text" name="description" required/>
                </div>

                <div>
                    <label>Lecturer</label>
                    <select name = "lecturer" required>
                        {
                            lecturers.map(lecturer =>
                                <option key={lecturer.id} value={lecturer.username}>
                                    {`Name: ${lecturer.firstName} ${lecturer.lastName} \t Username: ${lecturer.username}`}
                                </option>)
                        }
                    </select>
                </div>

                <div>
                    <input type="submit" value = "Add Subject"/>
                </div>
            </form>
        </div>
    </div>)
}

export default AddSubject;