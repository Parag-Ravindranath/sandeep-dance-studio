package com.project.sds.models;

import java.time.ZonedDateTime;

public class AttendanceResponse {

    private String studentId;
    private String studentName;
    private ZonedDateTime attendedDate;
    private String batchId;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public ZonedDateTime getAttendedDate() {
        return attendedDate;
    }

    public void setAttendedDate(ZonedDateTime attendedDate) {
        this.attendedDate = attendedDate;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    public AttendanceResponse(String studentId, ZonedDateTime attendedDate, String batchId) {
        this.studentId = studentId;
        this.attendedDate = attendedDate;
        this.batchId = batchId;
    }
}
