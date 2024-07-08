package com.exam.repo;

import com.exam.entity.Result;
import com.exam.entity.SolvedQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SolvedQuestionRepository extends JpaRepository<SolvedQuestion, Long> {

    @Query("SELECT COUNT(sq) FROM SolvedQuestion sq WHERE sq.quesId = ?1 AND sq.givenAnswer = ?2")
    long countByQuesIdAndGivenAnswer(long quesId, String givenAnswer);

    @Query("SELECT sq.answer FROM SolvedQuestion sq WHERE sq.quesId = ?1")
    String findAnswerByQuesId(long quesId);
    List<SolvedQuestion> findByQuizIdAndBatchId(Long quizId, Long batchId);
    List<SolvedQuestion> findByQuizId(Long quizId);
    List<SolvedQuestion> findByBatchId(Long batchId);


}

