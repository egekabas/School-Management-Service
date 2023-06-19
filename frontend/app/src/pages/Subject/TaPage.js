import {useEffect, useState} from "react";
import {Button} from "reactstrap";

function TaPage(props){
    const user = props.user;
    const id = props.id;

    const [subject, setSubject] = useState(false);

    useEffect(() => {
        fetch(`/getSubjectShallow/${id}`).then(response => response.json()).catch(() => window.alert("Could not retrieve subject details"))
            .then(subject => setSubject(subject));
    }, []);

    return (
        <div>{
            subject &&
            <div>
                <div> You are a TA for {subject.name}. Talk to the subject prof for further instructions</div>
                <Button onClick={async event => {
                    const message = await fetch(`/quitTaJob/${id}`, {method: 'POST'})
                        .then(response => response.text());
                    if(message === "Quit TA Job Successfully") {
                        window.location.reload();
                    } else{
                        window.alert(message);
                    }
                }}> Quit TA position</Button>
            </div>
        }</div>
    )
}

export default TaPage;