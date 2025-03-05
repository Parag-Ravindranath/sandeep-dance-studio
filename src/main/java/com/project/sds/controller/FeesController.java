package com.project.sds.controller;

import com.project.sds.models.Fees;
import com.project.sds.repository.FeesRepository;
import com.project.sds.service.FeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sds/fees")
public class FeesController {

    @Autowired
    private FeesService feesService;

    @Autowired
    private FeesRepository feesRepository;

    @PostMapping("/save")
    public ResponseEntity<?> feesStatus(@RequestBody Fees fees) {
        return ResponseEntity.ok(feesService.save(fees));
    }

    @DeleteMapping("/delete/{studentId}")
    public void delete(@PathVariable String studentId) {
        feesRepository.deleteByStudentId(studentId);
    }
}
