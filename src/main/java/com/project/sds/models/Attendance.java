package com.project.sds.models;

import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;

public class Attendance {

    @Id
    private String id;
    private String StudId;
    private ZonedDateTime attendedDate;

    public ZonedDateTime getAttendedDate() { return attendedDate; }
    public void setAttendedDate(ZonedDateTime attendedDate) { this.attendedDate = attendedDate; }

    public String getStudId() {
        return StudId;
    }

    public void setStudId(String studId) { StudId = studId; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
