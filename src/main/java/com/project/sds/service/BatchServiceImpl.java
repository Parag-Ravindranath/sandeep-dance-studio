package com.project.sds.service;

import com.project.sds.models.Batch;
import com.project.sds.repository.BatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BatchServiceImpl implements BatchService {

    @Autowired
    private BatchRepository batchRepository;
    @Override
    public Batch save(Batch batch) {
        return batchRepository.save(batch);
    }

    @Override
    public List<Batch> listAll() {
        return batchRepository.findAll();
    }
}
