import {useEffect, useState} from "react";
import DisplaySubject from "./DisplaySubject";

function ListSubjects(props){

    const [subjects, setSubjects] = useState([]);

    useEffect(() => {
        fetch('/getAllSubjects').then(response => response.json()).then(subjects => {
            setSubjects(subjects);
        }).catch(() => window.alert("Could not load subjects"));
    }, []);


    return (<div>
        {subjects.map(subject => <DisplaySubject subject = {subject} key = {subject.id}/>)
        }
    </div>)
}

export default ListSubjects;