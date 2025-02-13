package com.project.sds.controller;

import com.project.sds.models.Batch;
import com.project.sds.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.prefs.InvalidPreferencesFormatException;


@RestController
@RequestMapping("/api/sds/batch")
public class BatchController {


    @Autowired
    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveBatch(@RequestBody Batch batch)  {
        return ResponseEntity.ok(batchService.save(batch));
    }

    @GetMapping("/list")
    public ResponseEntity<List<Batch>> getAllList() {
        return ResponseEntity.ok(batchService.listAll());
    }
}
