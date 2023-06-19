function Students(props){
    const subject = props.subject;

    return (
        <div>
            {subject.students.map(student => <div key = {student.id}>
                {student.firstName + ' ' + student.lastName}
            </div>)}
        </div>
    )
}
export default Students;