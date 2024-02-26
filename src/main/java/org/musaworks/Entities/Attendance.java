package org.musaworks.Entities;

import java.sql.Date;

public class Attendance {
    private int attendanceId;

    private int studentId;

    private Date dateAttended;
    private Boolean excuseDate;

    public Attendance(int attendanceId, int studentId, Date dateAttended, boolean excuseDate) {
        this.attendanceId = attendanceId;
        this.studentId = studentId;
        this.dateAttended = dateAttended;
        this.excuseDate = excuseDate;
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

    public Boolean isExcuseDate() {
        return excuseDate;
    }

    public void setExcuseDate(Boolean excuseDate) {
        this.excuseDate = excuseDate;
    }
}
