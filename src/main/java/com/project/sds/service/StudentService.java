package com.project.sds.service;

import com.project.sds.models.Student;
import com.project.sds.models.StudentResponse;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student save(Student student);

    List<Student> listAll();

   List<StudentResponse> listByBatch(String batchId);

    Optional<Student> listById(String id);

    void deleteById(String id);
}
