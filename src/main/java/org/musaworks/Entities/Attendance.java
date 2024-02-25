package org.musaworks.Entities;

import java.sql.Date;

public class Attendance {
    private int attendanceId;

    private int studentId;

    private Date dateAttended;

    public Attendance(int attendanceId, int studentId, Date dateAttended) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.dateAttended = dateAttended;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public Date getDateAttended() {
        return dateAttended;
    }

    public void setDateAttended(Date dateAttended) {
        this.dateAttended = dateAttended;
    }
}
