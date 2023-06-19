package school_management.CRUD.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import school_management.CRUD.controller.requestRecords.*;
import school_management.CRUD.entities.*;
import school_management.CRUD.service.DatabaseService;

import java.util.List;

@RestController
@AllArgsConstructor
public class DatabaseController {

    private final DatabaseService databaseService;

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public List<UserEntity> getAllUsers(){
        return databaseService.loadAllUsers();
    }

    @GetMapping("/getAllSubjects")
    @ResponseBody
    public List<ShallowSubject> getAllSubjects(){
        return databaseService.loadAllSubjects();
    }

    @GetMapping("/getMySubjects")
    @ResponseBody
    public List<ShallowSubject> getMySubjects(){
        return databaseService.loadMySubjects();
    }

    @PostMapping("/enrollInSubject/{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    public String enrollInSubject(@PathVariable("id") long id){return databaseService.enrollInSubject(id);};

    @GetMapping("/checkSubjectAccess/{id}")
    @ResponseBody
    public boolean checkSubjectAccess(@PathVariable("id") long id){return databaseService.checkSubjectAccess(id);}

    @GetMapping("/getSubjectShallow/{id}")
    @ResponseBody
    public ShallowSubject getSubjectShallow(@PathVariable("id") long id) {return databaseService.loadSubjectShallow(id);}

    @GetMapping("/getSubjectDeep/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'TA', 'LECTURER')")
    @ResponseBody
    public SubjectEntity getSubjectDeep(@PathVariable("id") long id) {return databaseService.loadSubjectDeep(id);}

    @PostMapping("/deleteSubject/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    @ResponseBody
    public String deleteSubject(@PathVariable("id") long id) {return databaseService.deleteSubject(id);};

    @PostMapping("/acceptTaApplication/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    @ResponseBody
    public String acceptTaApplication(@PathVariable("id") long id) {return databaseService.acceptTaApplication(id);}

    @PostMapping("/rejectTaApplication/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    @ResponseBody
    public String rejectTaApplication(@PathVariable("id") long id) {return databaseService.rejectTaApplication(id);}

    @PostMapping("/quitTaJob/{id}")
    @PreAuthorize("hasAuthority('TA')")
    @ResponseBody
    public String quitTaJob(@PathVariable("id") long id) {return databaseService.quitTaJob(id);}

    @PostMapping("/fireTa")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    @ResponseBody
    public String fireTa(@Valid @RequestBody FireTaRequest request) {return databaseService.fireTa(request);}

    @PostMapping("/addAssignment")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    @ResponseBody
    public String addAssignment(@Valid @RequestBody AddAssignmentRequest request){return databaseService.addAssignment(request);}

    @DeleteMapping("/deleteAssignment/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    @ResponseBody
    public String deleteAssignment(@PathVariable("id") long assignmentId){return databaseService.deleteAssignment(assignmentId);}

    @GetMapping("/getMyGrades/{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    @ResponseBody
    public List<GradeEntity> getMyGrades(@PathVariable("id") long subjectId){return databaseService.getMyGrades(subjectId);};

    @PostMapping("/dropClass/{id}")
    @PreAuthorize("hasAuthority('STUDENT')")
    @ResponseBody
    public String dropClass(@PathVariable("id") long subjectId){return databaseService.dropClass(subjectId);}

    @GetMapping("/getAssignmentGrades/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    @ResponseBody
    public List<GradeEntity> getAssignmentGrades(@PathVariable("id") long assignmentId){return databaseService.getAssignmentGrades(assignmentId);}

    @PostMapping("/setGrade/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'LECTURER')")
    @ResponseBody
    public String setGrade(@PathVariable("id") long gradeId, @Valid @RequestBody int gradeVal){
        return databaseService.setGrade(gradeId, gradeVal);
    }

    @PostMapping("/applyForTa")
    @PreAuthorize("hasAuthority('TA')")
    @ResponseBody
    public String applyForTa(@RequestBody @Valid TaApplicationRequest request){return databaseService.applyForTa(request);}

    @GetMapping("/checkIfApplied/{id}")
    @PreAuthorize("hasAuthority('TA')")
    @ResponseBody
    public boolean checkIfApplied(@PathVariable("id") long id){return databaseService.checkIfApplied(id);}

    @GetMapping("/getAllLecturers")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public List<UserEntity> getAllLecturers(){ return databaseService.loadAllByRole(Role.LECTURER);};

    @GetMapping("/getSubjectsTaught/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public List<SubjectEntity> getSubjectsTaught(@PathVariable("id") long id){ return databaseService.loadSubjectsTaught(id);};

    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addUser(@RequestBody @Valid NewUserRequest userRequest) { return databaseService.addNewUser(userRequest); }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteUser(@PathVariable("id") @Valid long id){ return databaseService.deleteByID(id);}

    @PostMapping("/addSubject")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addSubject(@RequestBody @Valid NewSubjectRequest subjectRequest){
        return databaseService.addNewSubject(subjectRequest);
    }

    @GetMapping("/getCurrentAuthorities")
    @ResponseBody
    public Object getCurrentAuthorities() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }

    @GetMapping("/getIfAuthenticated")
    public boolean getIfAuthenticated(Authentication authentication){
        return authentication != null;
    }

    @GetMapping("/getCurrentUser")
    @ResponseBody
    public UserEntity getCurrentUser(){
        return databaseService.loadCurrentUser();
    }


    @PostMapping("/changePassword")
    @ResponseBody
    public String changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        return databaseService.changePassword(request.oldPassword(), request.newPassword());
    }
}
