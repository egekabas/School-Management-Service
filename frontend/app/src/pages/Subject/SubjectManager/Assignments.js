import {Link, Route, Routes, useParams} from "react-router-dom";
import React, {useEffect, useState} from "react";
import {Button} from "reactstrap";
import './Assignments.css'

function Assignments(props){


    return (
        <div>
            <Routes>
                <Route path = '/' element={<ListAssignments subject = {props.subject}/>}/>
                <Route path = '/:id' element={<ViewAssignment subject = {props.subject}/>}/>
            </Routes>
        </div>
    )
}
function ViewAssignment(props){
    const {id} = useParams();
    const subject = props.subject;

    const [assignment] = subject.assignments.filter(assignment => assignment.id == id);

    const [grades, setGrades] = useState([]);

    useEffect(() => {
        fetch(`/getAssignmentGrades/${id}`).then(response => response.json())
            .then(grades => setGrades(grades)).catch(() => window.alert("Could not load grades"));
    }, []);

    console.log(grades);

    return (<div>
        <div className="title">Grades for assignment {assignment.description}</div>
        <div>
            {grades.map(grade => (
                <div key = {grade.id} className="grade-container">
                    <div>
                        {grade.student.firstName + ' ' + grade.student.lastName}
                    </div>
                    <form onSubmit={async event => {
                        event.preventDefault();
                        const message = await fetch(`/setGrade/${grade.id}`, {
                            method: "POST",
                            headers: {'Content-Type':'application/json'},
                            body: event.target.score.value
                        }).then(response => response.text());
                        if(message !== "Grade set successfully"){
                            window.alert(message);
                        }
                    }}>
                        <label>Score </label>
                        <input type="number" name="score" required defaultValue={grade.graded ? grade.grade : ""}/>
                        <input type="submit" value = "Update Score"/>
                    </form>
                </div>
            ))}
        </div>
        <div className='go-back-container'>
            <Link to = '../' >Go back to the list of assignments</Link>
        </div>
    </div>);
}

function ListAssignments(props){
    const subject = props.subject;

    const handleSubmit = async event => {
        event.preventDefault();
        const body = JSON.stringify({
            subjectId: subject.id,
            description: event.target.description.value
        });
        const message = await fetch("/addAssignment", {method: 'POST', headers: {'Content-Type':'application/json'}, body: body})
            .then(response => response.text());

        if(message === "Assignment Added Successfully"){
            window.location.reload();
        } else{
            window.alert(message);
        }
    };

    return (
        <div>
            <div className="title">Current Assignments for Class</div>
            <div>
                {subject.assignments.map(assignment => (
                    <div key = {assignment.id} className="list-element">
                        <Link to = {`${assignment.id}`} key = {assignment.id} className="subject-link">{assignment.description}</Link>
                        <Button onClick={async event => {
                            if(window.confirm("Are you sure?")) {
                                event.preventDefault();
                                const message = await fetch(`/deleteAssignment/${assignment.id}`, {method: "DELETE"})
                                    .then(response => response.text());
                                if (message !== "Assignment Deleted Successfully") {
                                    window.alert(message);
                                } else {
                                    window.location.reload();
                                }
                            }
                        }} className="delete-button">Delete</Button>
                    </div>

                ))}
            </div>
            <div className="title">Add New Assignment</div>
            <form onSubmit={handleSubmit}>

                <div className="description-container">
                    <label>Assignment Description </label>
                    <input type="text" name="description" required />
                </div>

                <div>
                    <input type="submit" value = "Add Assignment"></input>
                </div>
            </form>
        </div>
    );
}



export default Assignments;