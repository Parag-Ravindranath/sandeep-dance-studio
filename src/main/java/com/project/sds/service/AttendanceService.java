package com.project.sds.service;


import com.project.sds.models.Attendance;
import com.project.sds.models.AttendanceResponse;

import java.util.List;
import java.util.Optional;

public interface AttendanceService {


    Attendance save(Attendance attendance);

    List<Attendance> findByStudId(String StudId);

    List<AttendanceResponse> getAttendanceByStudentId(String studentId);
}

