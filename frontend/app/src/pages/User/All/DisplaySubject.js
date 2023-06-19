import {Link} from "react-router-dom";

function DisplaySubject(props){
    const subject = props.subject;

    return (<div>
        <Link to = {`/subject/${subject.id}`}>{subject.name}</Link>
        <div>
            <div>Taught by {subject.lecturer.firstName + ' ' + subject.lecturer.lastName}</div>
            <p>{subject.description}</p>
        </div>
    </div>);
}

export default DisplaySubject;