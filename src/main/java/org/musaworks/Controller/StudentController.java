package org.musaworks.Controller;

import org.musaworks.Entities.Student;
import org.musaworks.Service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/students")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @PostMapping("/register")
    public void registerStudent(@RequestBody StudentRegistrationRequest inRequest){
        String name = inRequest.name();
        double points = inRequest.points();

        Student studentToRegister = new Student(name, points);
        studentService.registerStudent(studentToRegister);

    }

    @PostMapping("/addPointsByName")
    public void addPointsByName(@RequestBody AddPointsRequest addPointsRequest){
        int addedPoints = addPointsRequest.addPoints();
        String name = addPointsRequest.studentName();

        studentService.addPointsByName(name, addedPoints);
    }

    @GetMapping("/searchStudent/{nameQuery}")
    public List<Student> searchStudentByName(@PathVariable String nameQuery){
        return studentService.searchByName(nameQuery);
    }

    @GetMapping("/getAllStudents")
    public List<Student> getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/topTen")
    public List<Student> getTopTen(){
        return studentService.getTopTen();
    }

    @PostMapping("/checkInStudents")
    public List<String> checkInStudents(@RequestBody List<CheckInRequest> checkInRequests){
        return studentService.checkInStudents(checkInRequests);
    }
}
