package com.exam.controller;

import com.exam.entity.Batch;
import com.exam.model.exams.BatchModel;
import com.exam.repo.BatchRepository;
import com.exam.services.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/batch")
@CrossOrigin("*")
public class BatchController {

    @Autowired
    private BatchService batchService;
    private BatchRepository batchRepository;
    @GetMapping("/")
    public ResponseEntity<?> getBatches()
    {
        return ResponseEntity.ok(this.batchService.getBatches());
    }
@PostMapping("/")
    public ResponseEntity<?> createBatch(@RequestBody BatchModel batchModel)
    {
        return ResponseEntity.ok(this.batchService.addBatch(batchModel));
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getBatchById(@PathVariable("id") long id) {
        Optional<BatchModel> batch = batchService.getBatchById(id);
        return batch.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBatch(@PathVariable("id") long id) {
        try {
            System.out.println("Delete batch id : "+id);
          //  batchRepository.deleteById(id);
            batchService.deleteBatch(id);
            return ResponseEntity.ok("{\"status\":\"Success\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBatch(@PathVariable("id") long id, @RequestBody Batch updatedBatch) {
       // System.out.println("Batch Update : "+updatedBatch.toString());
        try {
        return new ResponseEntity<>(batchService.updateBatch(id, updatedBatch), HttpStatus.OK);
     } catch (Exception e) {
        return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

}
