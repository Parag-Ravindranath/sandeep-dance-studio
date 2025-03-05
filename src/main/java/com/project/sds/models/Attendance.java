package com.project.sds.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;


@Data
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "attendance")
public class Attendance {

    @Id
    private String id;
    private String studentId;
    private String attendedDate;

    public String getAttendedDate() {
        return attendedDate;
    }

    public void setAttendedDate(String attendedDate) {
        this.attendedDate = attendedDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
