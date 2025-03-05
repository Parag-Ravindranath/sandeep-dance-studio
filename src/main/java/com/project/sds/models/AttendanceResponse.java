package com.project.sds.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class AttendanceResponse {

    private String studentId;
    private String studentName;
    private String batchId;
    private String batchName;
    private String attendedDate;
    private double feesAmount;
    private String paidMonth;


    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAttendedDate() {
        return attendedDate;
    }

    public void setAttendedDate(String attendedDate) {
        this.attendedDate = attendedDate;
    }

    public String getBatchId() {
        return batchId;
    }
    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getBatchName() {
        return batchName;
    }

    public double getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(double feesAmount) {
        this.feesAmount = feesAmount;
    }

    public String getPaidMonth() {
        return paidMonth;
    }

    public void setPaidMonth(String paidMonth) {
        this.paidMonth = paidMonth;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }
    public AttendanceResponse(String studentId, String studentName, String attendedDate, String batchId, String batchName) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.attendedDate = attendedDate;
        this.batchId = batchId;
        this.batchName = batchName;
    }

    public AttendanceResponse(String studentId, String studentName, String attendedDate, String batchId, String batchName, double feesAmount, String paidMonth) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.attendedDate = attendedDate;
        this.batchId = batchId;
        this.batchName = batchName;
        this.feesAmount = feesAmount;
        this.paidMonth = paidMonth;
    }
}
