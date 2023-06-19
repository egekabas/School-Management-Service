import {Button} from "reactstrap";

function SubjectDelete(props){


    return (
        <Button onClick={async event => {
            event.preventDefault();
            const delResult = await fetch(`/deleteSubject/${props.subject.id}`, {method: 'POST'})
                .then(response => response.text());

            if(delResult === "Subject Deleted Successfully"){
                window.confirm("Subject Deleted Successfully, taking to home page");
                window.location.replace('/');
            }
            else{
                window.alert(delResult);
            }
        }}>Delete Subject</Button>
    )
}


export default SubjectDelete;