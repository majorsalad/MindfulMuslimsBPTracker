package org.musaworks.Service;

import org.musaworks.Controller.CheckInRequest;
import org.musaworks.Entities.Student;
import org.musaworks.Repository.AttendanceDAO;
import org.musaworks.Repository.StudentDAO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    private StudentDAO studentRepository;
    private AttendanceDAO attendanceDAO;

    public StudentService(StudentDAO studentRepository, AttendanceDAO attendanceRepository){
        this.attendanceDAO = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    public void registerStudent(Student student){
        studentRepository.registerStudent(student);
    }

    private int getStudentIdByName(String name){
        return studentRepository.getStudentIdByName(name);
    }

    public void addPointsByName(String name, int addedPoints) {
        int studentId = getStudentIdByName(name);
        double pointsToAdd = addedPoints + (addedPoints * totalBonus(studentId));
        studentRepository.addPointsByName(name, pointsToAdd);
    }

    public double totalBonus(int studentId){
        return studentRepository.getTotalBonusById(studentId);
    }

    public List<Student> searchByName(String name){
        return studentRepository.searchByName(name);
    }

    public List<Student> getAllStudents(){
        return studentRepository.getAllStudents();
    }

    public List<Student> getTopTen(){
        return studentRepository.getTopTen();
    }

    public List<String> checkInStudents(List<CheckInRequest> checkInRequests){

        List<String> studentsThatNeedRegistry = new ArrayList<>();
        for(CheckInRequest checkIn : checkInRequests){
            String name = checkIn.studentName();
            boolean studentExists = studentExistsByName(name);
            if(!studentExists){
                studentsThatNeedRegistry.add(name);
                continue;
            }
            checkInByName(name);
            // 1) updateAttendanceBonus
            int studentId = getStudentIdByName(name);
            updateAttendanceBonus(studentId);
            // 2) add points
            /** Default attendance points are 5, this will attempt to add */
            addPointsByName(name, 5);
        }


        return studentsThatNeedRegistry;
    }

    public void checkInByName(String name){
        Student student = studentRepository.checkInStudentByName(name);

    }

    public void updateAttendanceBonus(int id){
        attendanceDAO.updateAttendanceBonus(id);
    }

    public boolean studentExistsByName(String name) {
        return studentRepository.studentExistsByName(name);
    }
}
