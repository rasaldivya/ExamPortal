package com.exam.repo;

import com.exam.entity.Batch;
import com.exam.entity.BatchUserMapping;
import com.exam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface BatchUserMappingRepo extends JpaRepository<BatchUserMapping, Long> {
    List<BatchUserMapping> findByBatch(Batch batch);

    List<BatchUserMapping> findByBatchId(Long batchId);
    List<BatchUserMapping> findByUser(User user);
    @Transactional
    @Modifying
    @Query("UPDATE BatchUserMapping bu SET bu.quizStatus = :quizStatus WHERE bu.batch.id = :batchId AND bu.user.id = :userId")
    int updateQuizStatusByBatchIdAndUserId(Long batchId, Long userId, String quizStatus);

    Optional<BatchUserMapping> findByBatchIdAndUserId(Long batchId, Long userId);


}
