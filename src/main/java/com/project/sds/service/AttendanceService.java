package com.project.sds.service;


import com.project.sds.models.Attendance;
import com.project.sds.models.AttendanceResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AttendanceService {


    Attendance save(Attendance attendance);

    List<Attendance> findByStudId(String StudId);

    List<AttendanceResponse> getAttendanceByStudentId(String studentId);

    ResponseEntity<byte[]> generateAttendanceCsv(HttpServletResponse servletResponse) throws Exception;
}

