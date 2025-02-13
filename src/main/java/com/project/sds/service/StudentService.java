package com.project.sds.service;

import com.project.sds.models.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student save(Student student);

    List<Student> listAll();

    List<Student> listByBatch(String batchId);

    Optional<Student> listById(String id);

    void deleteById(String id);
}
