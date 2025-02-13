package com.project.sds.repository;

import com.project.sds.models.Batch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BatchRepository extends MongoRepository<Batch, String> {

    List<Batch> findAll();

}
