package com.project.sds.repository;

import com.project.sds.models.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;


@Repository
public interface AttendanceRepository extends MongoRepository<Attendance ,String> {

    List<Attendance> findByStudentId(String studentId);

    List<Attendance> findByAttendedDate(String todayDate);

    void deleteByStudentId(String studentId);

    void deleteAllByStudentId(String studentId);
}
