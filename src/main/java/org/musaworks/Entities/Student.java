package org.musaworks.Entities;

public class Student {

    private Integer studentId;
    private String studentName;
    private double barakahPoints;
    private double attendanceBonus;
    private double recruitmentBonus;
    private double totalBonus;

    public Student(String studentName, double barakahPoints){
        this.studentName = studentName;
        this.barakahPoints = barakahPoints;
    }

    public Student(int id, String studentName, double barakahPoints, double attendanceBonus, double recruitmentBonus, double totalBonus){
        this.studentId = id;
        this.studentName = studentName;
        this.barakahPoints = barakahPoints;
        this.attendanceBonus = attendanceBonus;
        this.recruitmentBonus = recruitmentBonus;
        this.totalBonus = totalBonus;
    }

    public Student(String studentName, double barakahPoints, double attendanceBonus, double recruitmentBonus, double totalBonus) {
        this.studentName = studentName;
        this.barakahPoints = barakahPoints;
        this.attendanceBonus = attendanceBonus;
        this.recruitmentBonus = recruitmentBonus;
        this.totalBonus = totalBonus;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public double getBarakahPoints() {
        return barakahPoints;
    }

    public void setBarakahPoints(double barakahPoints) {
        this.barakahPoints = barakahPoints;
    }

    public double getAttendanceBonus() {
        return attendanceBonus;
    }

    public void setAttendanceBonus(double attendanceBonus) {
        this.attendanceBonus = attendanceBonus;
    }

    public double getRecruitmentBonus() {
        return recruitmentBonus;
    }

    public void setRecruitmentBonus(double recruitmentBonus) {
        this.recruitmentBonus = recruitmentBonus;
    }

    public double getTotalBonus() {
        return totalBonus;
    }

    public void setTotalBonus(double totalBonus) {
        this.totalBonus = totalBonus;
    }
}
