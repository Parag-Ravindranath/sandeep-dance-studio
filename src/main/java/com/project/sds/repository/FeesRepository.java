package com.project.sds.repository;

import com.project.sds.models.Fees;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface FeesRepository extends MongoRepository<Fees, String> {

    List<Fees> findAllByPaidMonth(String studentId);

    Optional<Fees> findByStudentId(String studentId);

    void deleteByStudentId(String studentId);

    void deleteAllByStudentId(String studentId);
}
