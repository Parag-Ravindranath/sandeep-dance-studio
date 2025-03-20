package com.project.sds.controller;

import com.project.sds.models.Attendance;
import com.project.sds.models.AttendanceResponse;
import com.project.sds.repository.AttendanceRepository;
import com.project.sds.service.AttendanceService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/sds/attendance")
public class AttendanceController {

    @Autowired
    private  AttendanceService attendanceService;
    
    @Autowired
    private AttendanceRepository attendanceRepository;

    public AttendanceController(AttendanceService attendanceService) { this.attendanceService = attendanceService; }

    @PostMapping("/save")
    public ResponseEntity<?> markAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceService.save(attendance));
    }

    @GetMapping("/list/{StudId}")
    public ResponseEntity<List<Attendance>> attendedStudentListByStudId(@PathVariable String StudId) {
        return ResponseEntity.ok(attendanceService.findByStudId(StudId));
    }
    @GetMapping("/{studentId}")
    public List<AttendanceResponse> getAttendanceByStudentId(@PathVariable String studentId) {
        return attendanceService.getAttendanceByStudentId(studentId);
    }

    @DeleteMapping("/delete/{studentId}")
    public void delete(@PathVariable String studentId) {
        attendanceRepository.deleteByStudentId(studentId);
    }

    @GetMapping("/all/csv")
    public ResponseEntity<byte[]> getAllAttendanceCsv(HttpServletResponse response) throws Exception {
        return attendanceService.generateAttendanceCsv(response);
    }
}
