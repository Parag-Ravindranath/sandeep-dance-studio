package com.project.sds.service;


import com.project.sds.models.Attendance;
import com.project.sds.models.AttendanceResponse;
import com.project.sds.models.Student;
import com.project.sds.repository.AttendanceRepository;
import com.project.sds.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private  AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Attendance save(Attendance attendance) {
        attendance.setAttendedDate(ZonedDateTime.now());
        return attendanceRepository.save(attendance);
    }

    @Override
    public List<Attendance> findByStudId(String StudId) {
        return attendanceRepository.findByStudentId(StudId);
    }

    @Override
    public List<AttendanceResponse> getAttendanceByStudentId(String studentId) {
        List<Attendance> attendanceList = attendanceRepository.findByStudentId(studentId);
        return attendanceList.stream().map(attendance -> {
            Optional<Student> student = studentRepository.findById(attendance.getStudentId());
            return new AttendanceResponse(attendance.getStudentId(), attendance.getAttendedDate(), student.get().getBatchId());
        }).collect(Collectors.toList());
    }

}



