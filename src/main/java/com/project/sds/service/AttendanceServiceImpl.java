package com.project.sds.service;


import com.project.sds.models.Attendance;
import com.project.sds.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;



@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private  AttendanceRepository attendanceRepository;

    @Override
    public Attendance save(Attendance attendance) {
        attendance.setAttendedDate(ZonedDateTime.now());
        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance findByStudId(String StudId) {
        return attendanceRepository.findByStudId(StudId);
    }
}

