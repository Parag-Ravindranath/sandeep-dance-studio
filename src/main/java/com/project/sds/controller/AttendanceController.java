package com.project.sds.controller;

import com.project.sds.models.Attendance;
import com.project.sds.models.AttendanceResponse;
import com.project.sds.repository.AttendanceRepository;
import com.project.sds.service.AttendanceService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
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
    public void getAllAttendanceCsv(HttpServletResponse response) throws Exception {
        File csvFile = attendanceService.generateAttendanceCsv();
        response.setContentType("application/csv"); // Ensures proper download in all browsers
        response.setHeader("Content-Disposition", "attachment; filename=attendance_report.csv");
        response.setContentLength((int) csvFile.length());
        response.setCharacterEncoding("UTF-8");

        try (FileInputStream fis = new FileInputStream(csvFile);
             OutputStream os = response.getOutputStream()) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.flush();

        }
    }
}
