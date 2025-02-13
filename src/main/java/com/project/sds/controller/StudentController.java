package com.project.sds.controller;


import com.project.sds.models.Student;
import com.project.sds.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sds/student")
public class StudentController {


    @Autowired
    private final StudentService studentService;
    private Student student;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @PostMapping("/save")
    public ResponseEntity<?> addStudent(@RequestBody Student student) {
        return ResponseEntity.ok(studentService.save(student));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Student>> getStudentList() {
        return ResponseEntity.ok(studentService.listAll());
    }

    @GetMapping("/list/{batchId}")
    public ResponseEntity<List<Student>> getStudentListByBatchId(@PathVariable String batchId) {
        return ResponseEntity.ok(studentService.listByBatch(batchId));
    }

    @GetMapping("/print/{id}")
    public ResponseEntity<?> getStudentDataByStudId(@PathVariable String id) {
        return ResponseEntity.ok(studentService.listById(id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable String id) {
        this.studentService.deleteById(id);
        return ResponseEntity.ok("Student data has been successfully deleted");
    }
}