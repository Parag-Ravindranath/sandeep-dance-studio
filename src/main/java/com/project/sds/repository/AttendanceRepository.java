package com.project.sds.repository;

import com.project.sds.models.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;



public interface AttendanceRepository extends MongoRepository<Attendance ,String> {

    Attendance findByStudId(String StudId);
}
