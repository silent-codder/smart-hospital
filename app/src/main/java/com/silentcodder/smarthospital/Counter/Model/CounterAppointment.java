package com.silentcodder.smarthospital.Counter.Model;

public class CounterAppointment {

    String ChildName;
    String Problem;
    String AppointmentDate;
    String UserId;

    public CounterAppointment() {
    }

    public CounterAppointment(String childName, String problem, String appointmentDate, String userId) {
        ChildName = childName;
        Problem = problem;
        AppointmentDate = appointmentDate;
        UserId = userId;
    }

    public String getChildName() {
        return ChildName;
    }

    public void setChildName(String childName) {
        ChildName = childName;
    }

    public String getProblem() {
        return Problem;
    }

    public void setProblem(String problem) {
        Problem = problem;
    }

    public String getAppointmentDate() {
        return AppointmentDate;
    }

    public void setAppointmentDate(String appointmentDate) {
        AppointmentDate = appointmentDate;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
