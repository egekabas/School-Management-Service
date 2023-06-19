import {useEffect, useState} from "react";
import {Button} from "reactstrap";

function StudentPage(props){
    const user = props.user;
    const id = props.id;

    const [subject, setSubject] = useState(false);
    const [grades, setGrades] = useState([]);
    const [average, setAverage] = useState(undefined);


    useEffect(() => {
        fetch(`/getSubjectShallow/${id}`).then(response => response.json()).catch(() => window.alert("Could not retrieve subject details"))
            .then(subject => setSubject(subject));
    }, []);

    useEffect(() => {
        fetch(`/getMyGrades/${id}`).then(response => response.json()).catch(() => window.alert("Could not retrieve grades"))
            .then(grades => {
                setGrades(grades);
                const gradesCnt = grades.filter(grade => grade.graded).length;
                if(gradesCnt) {
                    const average = grades.filter(grade => grade.graded)
                        .reduce((curSum, grade) => curSum + grade.grade, 0) / gradesCnt;
                    setAverage(average);
                }
            });
    }, []);

    console.log(grades);
    return (
        <div>
            <div>Currently enrolled in {subject.name}</div>
            <div>GRADES</div>
            <div>{
                grades.map(grade => <div key ={grade.id}>
                    {grade.assignment.description + ": " + (grade.graded ? grade.grade : "Ungraded")}
                </div>)
            }</div>
            {average !== undefined && <div>AVERAGE: {average}</div>}
            <Button onClick={async event => {
                event.preventDefault();
                const message = await fetch(`/dropClass/${subject.id}`, {method: 'POST'})
                    .then(response => response.text());
                if(message !== "Class Dropped Successfully"){
                    window.alert(message);
                } else{
                    window.location.reload();
                }
            }}>Drop Class</Button>
        </div>
    )
}

export default StudentPage;