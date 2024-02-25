package org.musaworks.Repository;

import org.musaworks.Entities.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class StudentRepository implements StudentDAO {

    private final JdbcTemplate jdbcTemplate;
    private StudentRowMapper studentRowMapper;

    public StudentRepository(JdbcTemplate jdbcTemplate, StudentRowMapper studentRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.studentRowMapper = studentRowMapper;
    }

    @Override
    public void registerStudent(Student studentToRegister) {
        String sql = """
                    INSERT INTO student(student_name,barakah_pts)
                    VALUES (?,?)
                """;

        jdbcTemplate.update(sql, studentToRegister.getStudentName(), studentToRegister.getBarakahPoints());
    }

    @Override
    public void addPointsByName(String name, double addedPoints) {
        String searchSql = """
                    SELECT * FROM student
                    WHERE student_name = ?
                """;

        List<Student> matchingStudents = jdbcTemplate.query(searchSql, studentRowMapper, name);

        if(matchingStudents.size() > 1){
            throw new RuntimeException("There's more than one student with this name.");
        } else if(matchingStudents.size() < 1){
            throw new RuntimeException("No students found with this name");
        }

        Student targetStudent = matchingStudents.get(0);
        double newTotalBP = targetStudent.getBarakahPoints() + addedPoints;

        String updatePtsSql = """
                    UPDATE student SET barakah_pts = ? WHERE student_name = ?
                """;

        jdbcTemplate.update(updatePtsSql, newTotalBP, name);
    }

    @Override
    public double getTotalBonusById(int id) {
        String totalBonusQuery = """
                    SELECT * FROM student
                    WHERE student_id = ?
                """;

        final Student student = jdbcTemplate.query(totalBonusQuery, studentRowMapper, id)
                .stream()
                .findFirst()
                .orElse(null);

        if(student == null){
            throw new RuntimeException("Student does not exist");
        }

        return student.getTotalBonus();
    }

    @Override
    public List<Student> searchByName(String name) {
        String sql = """
                    SELECT * FROM student
                    WHERE student_name LIKE ?
                """;
        String searchPattern = "%" + name + "%";
        return jdbcTemplate.query(sql, studentRowMapper, searchPattern);
    }

    @Override
    public List<Student> getAllStudents() {
        String sql = """
                    SELECT * FROM student
                """;
        return jdbcTemplate.query(sql, studentRowMapper);
    }


    @Override
    public List<Student> getTopTen() {
        String sql = """
                    SELECT * FROM student
                    ORDER BY barakah_pts DESC LIMIT 10;
                """;
        return jdbcTemplate.query(sql, studentRowMapper);
    }

    @Override
    public int getStudentIdByName(String name) {
        String sql = """
                    SELECT * FROM student
                    WHERE student_name = ?
                """;
        Student student = jdbcTemplate.query(sql, studentRowMapper, name)
                .stream()
                .findFirst()
                .orElse(null);

        if(student == null){
            throw new RuntimeException("Student does not exist");
        }

        return student.getStudentId();
    }

    @Override
    public boolean studentExistsByName(String name) {

        String sql = """
                    SELECT * FROM student
                    WHERE student_name = ?
                """;
        final List<Student> results = jdbcTemplate.query(sql, studentRowMapper, name);
        return results.size() > 0;
    }

    @Override
    public Student checkInStudentByName(String name) {
        String sql = """
                    SELECT * FROM student
                    WHERE student_name = ?
                """;
        Student student = jdbcTemplate.query(sql, studentRowMapper, name)
                .stream()
                .findFirst()
                .orElse(null);

        String checkInQuery = """
                    INSERT INTO attendance (student_id, date_attended) VALUES (?, ?)
                """;
        // java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        Date currentDate = new Date(System.currentTimeMillis());
//        LocalDate localDate = LocalDate.of(2024, 2, 17);
//        Date specificDate = Date.valueOf(localDate);

        jdbcTemplate.update(checkInQuery, student.getStudentId(), currentDate);
        return student;
    }
}
