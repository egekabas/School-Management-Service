package school_management.CRUD.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.text.RandomStringGenerator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import school_management.CRUD.controller.requestRecords.*;
import school_management.CRUD.entities.*;
import school_management.CRUD.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DatabaseService {

    private final UserRepository userRepository;
    private final SubjectRepository subjectRepository;
    private final TaApplicationRepository taApplicationRepository;
    private final AssignmentRepository assignmentRepository;
    private final GradeRepository gradeRepository;
    private final PasswordEncoder passwordEncoder;


    public List<UserEntity> loadAllUsers(){
        return userRepository.findAll();
    }

    public List<ShallowSubject> loadAllSubjects() {
        return subjectRepository.findAll().stream().map(subject -> subject.shallowInfo()).collect(Collectors.toList());
    };

    public ShallowSubject loadSubjectShallow(long subjectId) {
        SubjectEntity subject = subjectRepository.findById(subjectId).orElseThrow(
            () -> new RuntimeException("Subject not found")
        );
        return subject.shallowInfo();
    }

    public SubjectEntity loadSubjectDeep(long subjectId){
        if(!checkSubjectAccess(subjectId)){
            throw new RuntimeException("Dont have access to class");
        }
        return subjectRepository.findById(subjectId).orElseThrow(
                () -> new RuntimeException("Subject not found")
        );
    }

    public String deleteSubject(long subjectId){
        subjectRepository.deleteById(subjectId);
        return "Subject Deleted Successfully";
    }

    public String applyForTa(TaApplicationRequest request){
        UserEntity user = loadCurrentUser();
        SubjectEntity subject = subjectRepository.findByName(request.subjectName()).orElseThrow(
                () -> new RuntimeException("Subject not found")
        );
        taApplicationRepository.save(
                new TaApplicationEntity(request.applicationText(), subject, user)
        );
        return "Successfully Applied";
    }

    @Transactional
    public String acceptTaApplication(long applicationId){
        TaApplicationEntity application = taApplicationRepository.findById(applicationId).orElseThrow(
                () -> new RuntimeException("TA Application not found")
        );

        SubjectEntity subject = application.getSubject();
        UserEntity user = application.getUser();

        if(!checkSubjectAccess(subject.getId())){
            return "You do not have access for this action";
        }

        subject.getTas().add(user);
        subjectRepository.save(subject);

        taApplicationRepository.delete(application);
        return "Application Accepted successfully";
    }
    @Transactional
    public String rejectTaApplication(long applicationId){
        TaApplicationEntity application = taApplicationRepository.findById(applicationId).orElseThrow(
                () -> new RuntimeException("TA Application not found")
        );

        SubjectEntity subject = application.getSubject();
        UserEntity user = application.getUser();

        if(!checkSubjectAccess(subject.getId())){
            return "You do not have access for this action";
        }

        taApplicationRepository.delete(application);
        return "Application Rejected successfully";
    }

    public String quitTaJob(long subjectId){
        SubjectEntity subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new RuntimeException("Subject not found")
        );
        UserEntity user = loadCurrentUser();
        if(!subject.getTas().contains(user)){
            return "You were never a TA";
        } else{
            subject.getTas().remove(user);
            subjectRepository.save(subject);
            return "Quit TA Job Successfully";
        }
    }


    public boolean checkIfApplied(long subjectId){
        UserEntity user = loadCurrentUser();
        SubjectEntity subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new RuntimeException("Subject not found")
        );
        return taApplicationRepository.existsBySubjectAndUser(subject, user);
    }


    public List<UserEntity> loadAllByRole(Role role) { return userRepository.findAllByRole(role);};

    public UserEntity loadCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                "Not currently logged in"));
    }

    public String addNewUser(NewUserRequest userRequest) {
        String password = randomPasswordGenerator(10);
        UserEntity user = new UserEntity(userRequest, passwordEncoder.encode(password));
        userRepository.save(user);
        return password;
    }
    public String addNewSubject(NewSubjectRequest subjectRequest){
        UserEntity lecturer = userRepository.findByUsername(subjectRequest.lecturerUserName()).orElseThrow(
                () -> new UsernameNotFoundException("Lecturer cant be found")
        );
        if(lecturer.getRole() != Role.LECTURER){
            new RuntimeException("User is not a lecturer");
        }
        subjectRepository.save(new SubjectEntity(subjectRequest, lecturer));
        return "New subject successfully added";
    }

    @Transactional
    public String enrollInSubject(long subjectId){
        UserEntity user = loadCurrentUser();
        if(user.getRole() != Role.STUDENT){
            throw  new RuntimeException("Must be a student to enroll in classes");
        }
        SubjectEntity subject = subjectRepository.findById(subjectId).orElseThrow(() -> new UsernameNotFoundException(
                "Cannot find subject with id " + subjectId
        ));

        if(subject.getStudents().contains(user)){
            return "Already enrolled in subject " + subject.getName();
        } else{
            subject.getStudents().add(user);
            for(AssignmentEntity assignment : subject.getAssignments()){
                assignment.getGrades().add(new GradeEntity(user, assignment));
            }
            subjectRepository.save(subject);
            return "Successfully enrolled in subject";
        }
    }

    public boolean checkSubjectAccess(long subjectId){
        UserEntity user = loadCurrentUser();
        SubjectEntity subject = subjectRepository.findById(subjectId).orElseThrow(() -> new UsernameNotFoundException(
                "Cannot find subject with id " + subjectId
        ));

        if(user.getRole() == Role.ADMIN){
            return true;
        } else if(user.getRole() == Role.STUDENT){
            return subject.getStudents().contains(user);
        } else if(user.getRole() == Role.LECTURER){
            return subject.getLecturer() == user;
        } else{ //TA
            return subject.getTas().contains(user);
        }
    }

    public List<SubjectEntity> loadSubjectsTaught(long userId){
        UserEntity user = userRepository.findById(userId).orElseThrow(
                () -> new UsernameNotFoundException("User cant be found"));

        if(user.getRole() == Role.LECTURER){
            return user.getClassesTaught();
        } else{
            return new ArrayList<>();
        }
    }

    @Transactional
    public String addAssignment(AddAssignmentRequest request){
        if(!checkSubjectAccess(request.subjectId())){
            return "You do not have access";
        } else{

            SubjectEntity subject = subjectRepository.findById(request.subjectId()).orElseThrow(
                    () -> new RuntimeException("Subject not found")
            );

            AssignmentEntity assignment = new AssignmentEntity(request.description(), subject);
            for(UserEntity student : subject.getStudents()){
                assignment.getGrades().add(new GradeEntity(student, assignment));
            }
            assignmentRepository.save(assignment);

            return "Assignment Added Successfully";
        }
    }
    public String deleteAssignment(long assignmentId){
        AssignmentEntity assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                () -> new RuntimeException("Assignment not found")
        );
        if(!checkSubjectAccess(assignment.getSubject().id())){
            throw new RuntimeException("You do not have access");
        }
        assignmentRepository.delete(assignment);
        return "Assignment Deleted Successfully";
    }

    public List<GradeEntity> getAssignmentGrades(long assignmentId){
        AssignmentEntity assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                () -> new RuntimeException("Assignment not found")
        );
        if(!checkSubjectAccess(assignment.getSubject().id())){
            throw new RuntimeException("You do not have access");
        }
        return assignment.getGrades();
    }

    public List<GradeEntity> getMyGrades(long subjectId){
        UserEntity user = loadCurrentUser();
        SubjectEntity subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new RuntimeException("Subject not found")
        );
        if(!subject.getStudents().contains(user)){
            throw new RuntimeException("Not enrolled in class!");
        }

        return subject.getAssignments().stream().map(
                assignment -> gradeRepository.findByStudentAndAssignment(user, assignment).orElseThrow(
                        () -> new RuntimeException("Grade non-existent")
                )
        ).collect(Collectors.toList());
    }

    @Transactional
    public String dropClass(long subjectId){
        UserEntity user = loadCurrentUser();
        SubjectEntity subject = subjectRepository.findById(subjectId).orElseThrow(
                () -> new RuntimeException("Subject not found")
        );
        if(!user.getClassesTaken().contains(subject)){
            return "Already not taking the class";
        }

        for(AssignmentEntity assignment: subject.getAssignments()){
            gradeRepository.deleteAllByStudentAndAssignment(user, assignment);
        }

        subject.getStudents().remove(user);
        subjectRepository.save(subject);
        return "Class Dropped Successfully";
    }

    public String setGrade(long gradeId, int gradeVal){
        GradeEntity grade = gradeRepository.findById(gradeId).orElseThrow(
                () -> new RuntimeException("Grade not found")
        );
        if(!checkSubjectAccess(grade.getAssignment().getSubject().id())){
            return "You do not have access";
        }
        grade.assignGrade(gradeVal);
        gradeRepository.save(grade);
        return "Grade set successfully";
    }

    public String fireTa(FireTaRequest request){
        if(!checkSubjectAccess(request.subjectId())){
            return "You do not have access";
        } else{
            SubjectEntity subject = subjectRepository.findById(request.subjectId()).orElseThrow(
                    () -> new RuntimeException("Subject not found")
            );
            UserEntity ta = userRepository.findById(request.taId()).orElseThrow(
                    () -> new UsernameNotFoundException("TA not found")
            );
            subject.getTas().remove(ta);
            subjectRepository.save(subject);
            return "TA Fired Successfully";
        }
    }

    public String deleteByID(long userId){
        userRepository.deleteById(userId);
        return "Deleted Successfully";
    }

    private String randomPasswordGenerator(int length){
        RandomStringGenerator stringGenerator = new RandomStringGenerator.Builder().withinRange(33, 45).build();
        return stringGenerator.generate(length);
    }

    public String changePassword(String oldPassword, String newPassword){
        UserEntity user = loadCurrentUser();

        if(passwordEncoder.matches(oldPassword, user.getPassword())){
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return "Password changed successfully";
        } else{
            return "Old Password entered incorrectly";
        }
    }

    public List<ShallowSubject> loadMySubjects(){
        UserEntity user = loadCurrentUser();

        List<SubjectEntity> mySubjects;

        if(user.getRole() == Role.LECTURER){
            mySubjects = user.getClassesTaught();
        } else if(user.getRole() == Role.STUDENT){
            mySubjects = user.getClassesTaken();
        } else if(user.getRole() == Role.TA){
            mySubjects = user.getClassesTAd();
        } else{
            mySubjects = new ArrayList<>();
        }
        return mySubjects.stream().map(subject -> subject.shallowInfo()).collect(Collectors.toList());
    }
}
