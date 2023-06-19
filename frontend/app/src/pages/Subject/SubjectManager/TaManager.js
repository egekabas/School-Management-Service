import {Button} from "reactstrap";
import Popup from "reactjs-popup";
import './TAManager.css'

function TaManager(props){
    const subject = props.subject;

    return (
        <div>
            <div>
                <div className="title"> CURRENT TAS </div>
                {subject.tas.map(ta => (
                    <div key = {ta.id} className="container">
                        <div>{`${ta.firstName} ${ta.lastName}`}</div>
                        <Button onClick={async event => {
                            event.preventDefault();
                            const body = JSON.stringify({
                                subjectId: subject.id,
                                taId: ta.id
                            });
                            const message = await fetch("/fireTa", {method: 'POST', headers: {'Content-Type':'application/json'}, body: body})
                                .then(response => response.text());
                            if(message === "TA Fired Successfully"){
                                props.setSubject(false);
                            } else {
                                window.alert(message);
                            }
                        }}>Fire</Button>
                    </div>
                ))}
            </div>


            <div>
                <div className="title"> TA APPLICATIONS </div>
                {subject.taApplications.map(taApplication => (
                    <div key = {taApplication.id} className="container">

                        <div>
                            {`${taApplication.user.firstName} ${taApplication.user.lastName}`}
                        </div>

                        <Popup trigger = {<button>Application Text</button>}>
                            <div className="popup">{taApplication.applicationText}</div>
                        </Popup>

                        <Button onClick={async event => {
                            event.preventDefault();
                            const message = await fetch(`/acceptTaApplication/${taApplication.id}`, {method: 'POST'})
                                .then(response => response.text());
                            if(message === "Application Accepted successfully") {
                                props.setSubject(false);
                            } else {
                                window.alert(message);
                            }
                        }}>Accept</Button>

                        <Button onClick={async event => {
                            event.preventDefault();
                            const message = await fetch(`/rejectTaApplication/${taApplication.id}`, {method: 'POST'})
                                .then(response => response.text());
                            if(message === "Application Rejected successfully") {
                                props.setSubject(false);
                            } else{
                                window.alert(message);
                            }
                        }}>Reject</Button>

                    </div>
                ))}
            </div>
        </div>
    )

}

export default TaManager;