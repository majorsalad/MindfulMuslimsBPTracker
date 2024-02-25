package org.musaworks.Repository;

import org.musaworks.Entities.Attendance;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AttendanceRowMapper implements RowMapper<Attendance> {

    @Override
    public Attendance mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Attendance(rs.getInt("attendance_id"),rs.getInt("student_id"),rs.getDate("date_attended"));
    }

}
