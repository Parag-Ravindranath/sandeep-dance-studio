package com.project.sds.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Getter
@Setter
@NoArgsConstructor
@Document(collection = "fees")
public class Fees {

    @Id
    private String Id;
    private String studentId;

    private boolean feesStatus;

    private String paidMonth;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

        public String getPaidMonth() {
        return paidMonth;
    }

    public void setPaidMonth(String paidMonth) {
        this.paidMonth = paidMonth;
    }

    public boolean isFeesStatus() {
        return feesStatus;
    }

    public void setFeesStatus(boolean feesStatus) {
        this.feesStatus = feesStatus;
    }
}
