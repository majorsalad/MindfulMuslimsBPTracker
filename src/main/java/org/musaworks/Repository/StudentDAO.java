package org.musaworks.Repository;

import org.musaworks.Entities.Student;

import java.util.List;

public interface StudentDAO {
    void registerStudent(Student studentToRegister);

    void addPointsByName(String name, double addedPoints);

    double getTotalBonusById(int id);

    List<Student> searchByName(String name);

    List<Student> getAllStudents();
;
    List<Student> getTopTen();

    int getStudentIdByName(String name);

    boolean studentExistsByName(String name);

    Student checkInStudentByName(String name);

    void excuseStudentsCheckIn();
}
