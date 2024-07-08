package com.exam.services;
import com.exam.entity.BatchUserMapping;
import com.exam.exceptions.NotFoundException;
import com.exam.model.exams.BatchUserMappingModel;
import com.exam.repo.BatchUserMappingRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BatchUserMappingService {

    @Autowired
    private BatchUserMappingRepo batchUserMappingRepository;

    public void updateQuizStatus(Long batchId, Long userId, String quizStatus) {
        batchUserMappingRepository.updateQuizStatusByBatchIdAndUserId(batchId, userId, quizStatus);
    }

    public void deleteBatchUserMappingsByBatchId(Long batchId) {
        List<BatchUserMapping> mappings = batchUserMappingRepository.findByBatchId(batchId);
        batchUserMappingRepository.deleteAll(mappings);
    }

    // public Optional<BatchUserMapping> getBatchUserDeatils(Long batchId, Long userId) {
    //     Optional<BatchUserMapping> batchUserMappingOptional = batchUserMappingRepository.findByBatchIdAndUserId(batchId, userId);

    //     return batchUserMappingOptional;
    // }



    public BatchUserMappingModel updateStatus(Long batchId, Long userId, String status) {
        Optional<BatchUserMapping> batchUserMappingOptional = batchUserMappingRepository.findByBatchIdAndUserId(batchId, userId);

        if (batchUserMappingOptional.isPresent()) {
            BatchUserMapping batchUserMapping = batchUserMappingOptional.get();
            String previousStatus = batchUserMapping.getQuizStatus();


            if(status.equals("STARTED"))
            {
                if (previousStatus.equals("CREATED")) {
                   
                    batchUserMapping.setStartedAt(new Date());
                    
                    batchUserMapping.setQuizStatus(status);
                    batchUserMappingRepository.save(batchUserMapping);

                    return getBatchUserMappingModel(batchUserMapping);
                }  else {
                    return getBatchUserMappingModel(batchUserMapping);
                }
            } else if (status.equalsIgnoreCase("COMPLETED")) {
                if (previousStatus.equals("STARTED")) {

                    batchUserMapping.setCompletedAt(new Date());

                    batchUserMapping.setQuizStatus(status);
                    batchUserMappingRepository.save(batchUserMapping);

                    return getBatchUserMappingModel(batchUserMapping);
                }  else {
                    return getBatchUserMappingModel(batchUserMapping);
                }
            }else {
                return getBatchUserMappingModel(batchUserMapping);
            }

        } else {
            throw new NotFoundException("BatchUserMapping not found for given batchId and userId.");
        }
    }

    public BatchUserMappingModel getBatchUserMappingModel(BatchUserMapping batchUserMapping) {
        BatchUserMappingModel updatedDto = new BatchUserMappingModel();
        updatedDto.setBatchId(batchUserMapping.getBatch().getId());
        updatedDto.setUserId(batchUserMapping.getUser().getId());
        updatedDto.setStatus(batchUserMapping.getQuizStatus());
        updatedDto.setStartedAt(batchUserMapping.getStartedAt());
        updatedDto.setCompletedAt(batchUserMapping.getCompletedAt());
        updatedDto.setCreatedAt(batchUserMapping.getCreatedAt());
        updatedDto.setUpdatedAt(batchUserMapping.getUpdatedAt());
        return updatedDto;
    }

    // public BatchUserMappingModel getBatchUserMappingDetails(Long batchId, Long userId) {
    //     Optional<BatchUserMapping> batchUserMappingOptional = batchUserMappingRepository.findByBatchIdAndUserId(batchId, userId);

    //     if (batchUserMappingOptional.isPresent()) {
    //         BatchUserMapping batchUserMapping = batchUserMappingOptional.get();
    //         return getBatchUserMappingModel(batchUserMapping);
    //     } else {
    //         throw new NotFoundException("BatchUserMapping not found for given batchId and userId.");
    //     }
    // }
}