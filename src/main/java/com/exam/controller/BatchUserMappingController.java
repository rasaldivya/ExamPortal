package com.exam.controller;

import com.exam.entity.BatchUserMapping;
import com.exam.model.exams.BatchUserMappingModel;
import com.exam.services.BatchUserMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/batch-user-mapping")
@CrossOrigin("*")
public class BatchUserMappingController {

    @Autowired
    private BatchUserMappingService batchUserMappingService;

    @PatchMapping("/{batchId}/{userId}/{quizStatus}")
    public ResponseEntity<Void> updateQuizStatus(
            @PathVariable Long batchId,
            @PathVariable Long userId,
            @PathVariable String quizStatus) {
        batchUserMappingService.updateQuizStatus(batchId, userId, quizStatus);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping("/update/{batchId}/{userId}/{quizStatus}")
    public ResponseEntity<?> updateQuizStatusNow(
            @PathVariable Long batchId,
            @PathVariable Long userId,
            @PathVariable String quizStatus) {
        try {
//            batchUserMappingService.updateStatus(batchId, userId, quizStatus);
            return ResponseEntity.ok( batchUserMappingService.updateStatus(batchId, userId, quizStatus));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

//    @GetMapping("/batch/{batchId}/user/{userId}/status")
//    public ResponseEntity<String> getUserStatusForBatch(@PathVariable Long batchId, @PathVariable Long userId) {
//        Optional<BatchUserMapping> batchUserMappingOptional = batchUserMappingRepository.findByBatchIdAndUserId(batchId, userId);
//
//        if (batchUserMappingOptional.isPresent()) {
//            BatchUserMapping batchUserMapping = batchUserMappingOptional.get();
//            String status = batchUserMapping.getQuizStatus();
//            return ResponseEntity.ok(status);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }


    // @GetMapping("/batch/{batchId}/user/{userId}/status")
    // public ResponseEntity<?> getUserStatusForBatch(@PathVariable Long batchId, @PathVariable Long userId) {

    //     try {
    //         BatchUserMappingModel batchUserMappingOptional = batchUserMappingService.getBatchUserMappingDetails(batchId, userId);


    //         return ResponseEntity.ok(batchUserMappingOptional);
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
    //     }

    // }
}
