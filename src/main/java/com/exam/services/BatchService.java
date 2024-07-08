package com.exam.services;
import com.exam.entity.Batch;
import com.exam.model.exams.BatchModel;
import java.util.List;
import java.util.Optional;


public interface BatchService {

     List<BatchModel> getBatches();
     Batch addBatch(BatchModel batch);
     public Optional<BatchModel> updateBatch(long id, Batch updatedBatch) throws Exception;

     public Optional<BatchModel> getBatchById(long id);
     public void deleteBatch(Long batchId) throws Exception;
     public void deleteAllBatchesForQuiz(Long quizId);
}
