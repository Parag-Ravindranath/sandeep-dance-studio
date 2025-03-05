package com.project.sds.service;


import com.project.sds.models.Attendance;
import com.project.sds.models.Fees;
import com.project.sds.models.Student;
import com.project.sds.models.StudentResponse;
import com.project.sds.repository.AttendanceRepository;
import com.project.sds.repository.FeesRepository;
import com.project.sds.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private AttendanceRepository attendanceRepository;
    @Autowired
    private FeesRepository feesRepository;
    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<StudentResponse> listByBatch(String batchId) {
     return listAllWithFeesAndAttendance(batchId);
    }

    @Override
    public Optional<Student> listById(String id) {
        Optional<Student> student = studentRepository.findById(id);
        if (student.isPresent()) {
            return student;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Student data does not exists");
        }
    }

    @Override
    public void deleteById(String id) {
        attendanceRepository.deleteAllByStudentId(id);
        feesRepository.deleteByStudentId(id);
        studentRepository.deleteById(id);
        System.out.println("Student data has been successfully deleted");
    }
    public List<StudentResponse> listAllWithFeesAndAttendance(String batchId) {
        String monthYear = ZonedDateTime.now().format(DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH));

        List<Student> allStudents = studentRepository.findAllByBatchId(batchId);
        List<Fees> feesPaid = feesRepository.findAllByPaidMonth(monthYear);

        List<String> paidStudentIds = feesPaid.stream()
                .map(Fees::getStudentId).toList();

        List<String> paidStudents = allStudents.stream()
                .map(Student::getId).filter(paidStudentIds::contains).toList();
        String todayDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        List<Attendance> attendanceRecords = attendanceRepository.findByAttendedDate(todayDate);
        List<String> attendedStudentIds = attendanceRecords.stream()
                .map(Attendance::getStudentId).toList();

        List<String> attendedStudents = allStudents.stream()
                .map(Student::getId).filter(attendedStudentIds::contains).toList();
        // Convert to StudentResponse list
        List<StudentResponse> responseList = new ArrayList<>();
       for(Student student : allStudents){
           StudentResponse studentResponse = new StudentResponse();
           studentResponse.setStudentId(student.getId());
           studentResponse.setStudentName(student.getName());
           studentResponse.setBatchId(student.getBatchId());
           if(paidStudents.contains(student.getId()))
               studentResponse.setFeesPaid(true);
           if(attendedStudents.contains(student.getId()))
               studentResponse.setAttendanceStatus(true);
           responseList.add(studentResponse);
       }
       return responseList;
    }
}
