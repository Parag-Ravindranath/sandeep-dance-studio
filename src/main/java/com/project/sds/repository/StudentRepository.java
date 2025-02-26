package com.project.sds.repository;


import com.project.sds.models.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface StudentRepository extends MongoRepository<Student, String > {

    List<Student> findAllByBatchId(String batchId);
}
