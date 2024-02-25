package org.musaworks.Repository;

import org.musaworks.Entities.Student;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StudentRowMapper implements RowMapper<Student> {

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(rs.getInt("student_id"),
                rs.getString("student_name"),
                rs.getDouble("barakah_pts"),
                rs.getDouble("attendance_bonus"),
                rs.getDouble("recruitment_bonus"),
                rs.getDouble("total_bonus"));
    }

}
