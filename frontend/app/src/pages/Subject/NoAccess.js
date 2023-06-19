import button from "bootstrap/js/src/button";
import {Button} from "reactstrap";
import {useEffect, useState} from "react";
import {Link} from "react-router-dom";
import './NoAccess.css'

function NoAccess(props){
    const user = props.user;
    const id = props.id;

    const [subject, setSubject] = useState(false);

    useEffect(() => {
        fetch(`/getSubjectShallow/${id}`).then(response => response.json()).catch(() => window.alert("Could not retrieve subject details"))
            .then(subject => setSubject(subject));
    }, []);

    return (
        <div>
            {
                subject &&
                <div>
                    <div className="title">{subject.name} Website</div>

                    <p className="description-paragraph">
                    Description: {subject.description}<br/>
                    Taught by: {subject.lecturer.firstName + ' ' + subject.lecturer.lastName}<br/>
                    You currently do not have access to this subject.
                    </p>
                    { user.role === "STUDENT" && <EnrollButton user = {user} subject = {subject}/>}
                    { user.role === "TA" && <TaApplication user = {user} subject = {subject}/>}
                </div>
            }
        </div>
    )
}

function EnrollButton(props){
    const user = props.user;
    const subject = props.subject;

    const [errorMessage, setErrorMessage] = useState("");

    const handleCLick = async event => {
        event.preventDefault();
        const message = await fetch(`/enrollInSubject/${subject.id}`, {method: 'POST'}).then(response => response.text());
        if(message === "Successfully enrolled in subject"){
            window.location.reload();
        } else{
            setErrorMessage(message);
        }
    }

    return (
        <div>
            <Button variant = {"contained"} onClick = {handleCLick}>Enroll In class</Button>
            <div>{errorMessage}</div>
        </div>
    );
}

function TaApplication(props){
    const user = props.user;
    const subject = props.subject;

    const [alreadyApplied, setAlreadyApplied] = useState(true);

    useEffect(() => {
        const alreadyApplied = fetch(`/checkIfApplied/${subject.id}`).then(response => response.text()).then(
            alreadyApplied => {
                setAlreadyApplied(alreadyApplied === "true");
            }
        );
    }, []);

    const handleSubmit = async event => {
        event.preventDefault();
        const body = JSON.stringify({
            applicationText: event.target.applicationText.value,
            subjectName: subject.name
        });
        const message = await fetch('/applyForTa', {method: 'POST', headers: {'Content-Type':'application/json'}, body: body}).then(
            response => response.text()
        );
        if(message === "Successfully Applied") {
            window.location.reload();
        } else{
            window.alert(message);
        }
    }

    return (<div>
        {
            alreadyApplied ? <div>You already applied for a TAship here, wait for the result</div> :
                <div>
                    <div className="title">Apply for TAship</div>
                    <form onSubmit={handleSubmit}>

                        <div>
                            <div>Explain why you would be a good TA for this class</div>
                            <textarea name="applicationText" required className="paragraph-input"/>
                        </div>

                        <div>
                            <input type="submit" />
                        </div>

                    </form>
                </div>
        }
    </div>)
}

export default NoAccess;