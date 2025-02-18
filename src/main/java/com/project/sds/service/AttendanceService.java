package com.project.sds.service;


import com.project.sds.models.Attendance;

import java.util.List;
import java.util.Optional;

public interface AttendanceService {


    Attendance save(Attendance attendance);

    Attendance findByStudId(String StudId);
}

