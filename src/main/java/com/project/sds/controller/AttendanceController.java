package com.project.sds.controller;

import com.project.sds.models.Attendance;
import com.project.sds.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sds/attendance")
public class AttendanceController {

    @Autowired
    private  AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) { this.attendanceService = attendanceService; }

    @PostMapping("/save")
    public ResponseEntity<?> markAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.save(attendance));
    }

    @GetMapping("/list/{StudId}")
    public ResponseEntity<Attendance> attendedStudentListByStudId(@PathVariable String StudId) {
        return ResponseEntity.ok(attendanceService.findByStudId(StudId));
    }
}
