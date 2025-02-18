package com.project.sds.service;


import com.project.sds.models.Student;
import com.project.sds.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Override
    public Student save(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public List<Student> listAll() {
        return studentRepository.findAll();
    }

    @Override
    public List<Student> listByBatch(String batchId) {
     return studentRepository.findAllByBatchId(batchId);
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
        studentRepository.deleteById(id);
        System.out.println("Student data has been successfully deleted");
    }
}