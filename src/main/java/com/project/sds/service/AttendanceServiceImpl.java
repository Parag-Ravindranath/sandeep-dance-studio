package com.project.sds.service;


import com.project.sds.models.*;
import com.project.sds.repository.AttendanceRepository;
import com.project.sds.repository.BatchRepository;
import com.project.sds.repository.FeesRepository;
import com.project.sds.repository.StudentRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
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

    private static final Logger log = LoggerFactory.getLogger(AttendanceServiceImpl.class);

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
                    fees.map(Fees::getPaidMonth).orElse(null)
            ));
        }).filter(obj -> true).collect(Collectors.toList());
    }

    public List<FeeResponse> getAllFees() {
        List<FeeResponse> feeResponseList = new ArrayList<>();
        List<Student> studentList = studentRepository.findAll();
        studentList.forEach(student -> {
                    Optional<Fees> fees = feesRepository.findByStudentId(student.getId());

                    Optional<Batch> batch = batchRepository.findById(student.getBatchId());
                    if (fees.isPresent() && fees.get().isFeesStatus()) {
                        FeeResponse feeResponse = new FeeResponse();
                        feeResponse.setStudentName(student.getName());
                        feeResponse.setBatchName(batch.get().getName());
                        feeResponse.setFeesAmount(student.getFeesAmount());
                        feeResponse.setPaidMonth(fees.get().getPaidMonth());
                        feeResponseList.add(feeResponse);
                    }
                });
            return feeResponseList;
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

    public ResponseEntity<byte[]> generateAttendanceCsv(HttpServletResponse httpServletResponse) throws Exception {
        List<Optional<AttendanceResponse>> optionalList = getAllAttendance();
        List<AttendanceResponse> attendanceResponses = optionalList.stream().flatMap(Optional::stream).collect(Collectors.toList());

        List<FeeResponse> feesResponse = getAllFees();
        Workbook workbook = new XSSFWorkbook();

        // Attendance Sheet
        Sheet attendanceSheet = workbook.createSheet("Attendance");
        String[] attendanceColumns = {"StudentName", "BatchName", "AttendedDate", "Month", "Year"};
        writeHeader(attendanceSheet, attendanceColumns);
        writeAttendanceData(attendanceSheet, attendanceResponses);

        // Fees Sheet
        Sheet feesSheet = workbook.createSheet("Fees");
        String[] feesColumns = {"StudentName", "BatchName", "FeesAmount", "Month Year"};
        writeHeader(feesSheet, feesColumns);
        writeFeesData(feesSheet, feesResponse);

        // Prepare to stream the file
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        byte[] excelBytes = outputStream.toByteArray();

        // Prepare the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Students_Report.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .body(excelBytes);
    }

    private void writeHeader(Sheet sheet, String[] columns) {
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
        }
    }

    private void writeAttendanceData(Sheet sheet, List<AttendanceResponse> responses) {
        int rowNum = 1;
        for (AttendanceResponse response : responses) {
            String month = extractMonth(response.getAttendedDate());
            String year = extractYear(response.getAttendedDate());
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(response.getStudentName());
            row.createCell(1).setCellValue(response.getBatchName());
            row.createCell(2).setCellValue(response.getAttendedDate());
            row.createCell(3).setCellValue(month);
            row.createCell(4).setCellValue(year);
        }
    }

    private void writeFeesData(Sheet sheet, List<FeeResponse> responses) {
        int rowNum = 1;
        for (FeeResponse response : responses) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(response.getStudentName());
            row.createCell(1).setCellValue(response.getBatchName());
            row.createCell(2).setCellValue(response.getFeesAmount());
            row.createCell(3).setCellValue(response.getPaidMonth());
        }
    }
}



