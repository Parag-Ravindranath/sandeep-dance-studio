package com.project.sds.service;


import com.project.sds.models.*;
import com.project.sds.repository.AttendanceRepository;
import com.project.sds.repository.BatchRepository;
import com.project.sds.repository.FeesRepository;
import com.project.sds.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private  AttendanceRepository attendanceRepository;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private FeesRepository feesRepository;

    @Override
    public Attendance save(Attendance attendance) {
        attendance.setAttendedDate(formatDate(ZonedDateTime.now()));
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
            Optional<Batch> batch = batchRepository.findById(student.get().getBatchId());
            return new AttendanceResponse(attendance.getStudentId(),
                    student.get().getName(),
                    attendance.getAttendedDate(),
                    student.get().getBatchId(),
                    batch.get().getName());
        }).collect(Collectors.toList());
    }
    public List<Optional<AttendanceResponse>> getAllAttendance() {
        List<Attendance> attendanceList = attendanceRepository.findAll();

        return attendanceList.stream().map(attendance -> {
            Optional<Student> student = studentRepository.findById(attendance.getStudentId());
            Optional<Batch> batch = batchRepository.findById(student.get().getBatchId());
            Optional<Fees> fees = feesRepository.findByStudentId(student.get().getId());

            return student.map(s -> new AttendanceResponse(
                    attendance.getStudentId(),
                    student.get().getName(),
                    attendance.getAttendedDate(),
                    student.get().getBatchId(),
                    batch.get().getName(),
                    student.get().getFeesAmount(),
                    fees.get().getPaidMonth()
            ));
        }).filter(response -> response != null).collect(Collectors.toList());
    }

    private String formatDate(ZonedDateTime attendedDate) {
        if (attendedDate == null) {
            return "N/A"; // Handle null values safely
        }
        return attendedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));  // Extract YYYY-MM-DD
    }
    private String extractMonth(String attendedDate) {
        if (attendedDate == null || attendedDate.isEmpty()) {
            return "N/A";
        }
        try {
            // Parse the attendedDate string as LocalDate
            LocalDate date = LocalDate.parse(attendedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return date.format(DateTimeFormatter.ofPattern("MMMM", Locale.ENGLISH)); // e.g., "February"
        } catch (DateTimeParseException e) {
            return "Invalid Date"; // Handle parsing errors
        }
    }
    private String extractYear(String attendedDate) {
        if (attendedDate == null || attendedDate.isEmpty()) {
            return "N/A";
        }
        try {
            // Parse the attendedDate string as LocalDate
            LocalDate date = LocalDate.parse(attendedDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            return date.format(DateTimeFormatter.ofPattern("yyyy")); // e.g., "2024"
        } catch (DateTimeParseException e) {
            return "Invalid Date"; // Handle parsing errors
        }
    }

    public File generateAttendanceCsv() throws Exception {
        List<Optional<AttendanceResponse>> attendanceResponses = getAllAttendance();

        File csvFile = new File(System.getProperty("java.io.tmpdir"),"attendance_report.csv");
        try (FileWriter fileWriter = new FileWriter(csvFile);
             PrintWriter writer = new PrintWriter(fileWriter)) {
            writer.println("StudentName,BatchName,AttendedDate,Month,Year,FeesAmount,PaidMonth");
            for (Optional<AttendanceResponse> attendanceResponse : attendanceResponses) {
                attendanceResponse.ifPresent(response -> {
                    String month = extractMonth(response.getAttendedDate());
                    String year = extractYear(response.getAttendedDate());
                    writer.printf("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                            response.getStudentName(),
                            response.getBatchName(),
                            response.getAttendedDate(),
                            month, year,
                            response.getFeesAmount(),
                            response.getPaidMonth());
                });
            }
        }
        return csvFile;
    }
}



