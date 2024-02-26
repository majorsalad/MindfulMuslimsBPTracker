package org.musaworks.Repository;

import org.musaworks.Entities.Attendance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class AttendanceRepository implements AttendanceDAO{

    private final JdbcTemplate jdbcTemplate;
    private AttendanceRowMapper attendanceRowMapper;

    public AttendanceRepository(JdbcTemplate jdbcTemplate, AttendanceRowMapper attendanceRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.attendanceRowMapper = attendanceRowMapper;
    }

    private List<Attendance> getAttendanceDates(int student_id){
        String sql = "SELECT * FROM attendance WHERE student_id = ? ORDER BY date_attended;";
        return jdbcTemplate.query(sql, attendanceRowMapper, student_id);
    }

    @Override
    public void updateAttendanceBonus(int id) {
        String sql = "SELECT ARRAY_AGG(date_attended ORDER BY date_attended) as dates FROM attendance WHERE student_id = ? GROUP BY student_id";

        List<Attendance> dates = getAttendanceDates(id);

//        List<Date> dates = jdbcTemplate.query(sql, new Object[]{id}, rs -> {
//            List<Date> dateList = new ArrayList<>();
//            if (rs.next()) {
//                Date[] dateArray = (Date[]) rs.getArray("dates").getArray();
//                dateList.addAll(Arrays.asList(dateArray));
//            }
//            return dateList;
//        });

        if (!dates.isEmpty()) {
//            double bonus = calculateBonus(dates);
              double bonus = calculateAttendanceBonus(dates);
            String updateSql = "UPDATE student SET attendance_bonus = ? WHERE student_id = ?";
            jdbcTemplate.update(updateSql, bonus, id);
        }

    }

    public double calculateBonus(List<Date> dates){
        int streak = 1;
        double bonus = 0;
        for (int i = 1; i < dates.size(); i++) {
            LocalDate prevDate = dates.get(i - 1).toLocalDate();
            LocalDate currentDate = dates.get(i).toLocalDate();
            long diff = ChronoUnit.DAYS.between(prevDate, currentDate);
            //long diff = (dates.get(i).getTime() - dates.get(i - 1).getTime()) / (1000 * 60 * 60 * 24);
            if (diff == 6 || diff == 7) {
                streak++;
            } else if (diff > 8){
                streak = 1; // reset the streak if the difference between two dates is larger than 8 days (giving a one day grace period, incase I get tired and submit late)
            } else {
                streak = 1; // reset streak if not consecutive
            }

            // Update bonus based on streak
            if (streak == 2) {
                bonus = 0.1;
            } else if (streak == 3) {
                bonus = 0.15;
            } else if (streak == 4) {
                bonus = 0.20;
            } else if (streak == 5) {
                bonus = 0.25;
            } else if (streak == 6) {
                bonus = 0.5;
            } else if (streak == 7) {
                bonus = 0.75;
            } else if (streak == 8) {
                bonus = 1;
            }
        }
        return bonus;
    }

    public double calculateAttendanceBonus(List<Attendance> attendanceRecords) {
        int streak = 1;
        double bonus = 0;
        boolean wasPreviousExcuse = false; // Track if the previous record was an excused absence
        Attendance prevRecord = null;

        for (int i = 1; i < attendanceRecords.size(); i++) {
            if(!wasPreviousExcuse){
                prevRecord = attendanceRecords.get(i - 1);
            }
            Attendance currentRecord = attendanceRecords.get(i);

            LocalDate prevDate = prevRecord.getDateAttended().toLocalDate();
            LocalDate currentDate = currentRecord.getDateAttended().toLocalDate();
            long diff = ChronoUnit.DAYS.between(prevDate, currentDate);

            // If current record is an excused absence, note it and skip further processing for this iteration
            if (currentRecord.isExcuseDate()) {
                wasPreviousExcuse = true;
                continue;
            }

            if ((diff == 6 || diff == 7) && !wasPreviousExcuse) {
                // Regular attendance case
                streak++;
            } else if (diff == 13 || diff == 14) {
                // Handling the case where there's an excused absence in between
                // Only increment the streak if there was an excused absence previously
                if (wasPreviousExcuse) {
                    streak++;
                } else {
                    // Reset the streak if the gap is too large without an excused absence
                    streak = 1;
                }
            } else {
                // Reset the streak for any other case
                streak = 1;
            }

            // Reset the wasPreviousExcuse flag for the next iteration
            wasPreviousExcuse = false;

            // Update bonus based on the current streak
            switch (streak) {
                case 1:
                    bonus = 0;
                    break;
                case 2:
                    bonus = 0.1;
                    break;
                case 3:
                    bonus = 0.15;
                    break;
                case 4:
                    bonus = 0.20;
                    break;
                case 5:
                    bonus = 0.25;
                    break;
                case 6:
                    bonus = 0.5;
                    break;
                case 7:
                    bonus = 0.75;
                    break;
                case 8:
                    bonus = 1;
                    break;
            }
        }

        return bonus;
    }
}
