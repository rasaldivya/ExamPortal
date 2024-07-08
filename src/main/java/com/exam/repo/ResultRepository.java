package com.exam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Result;

public interface ResultRepository extends JpaRepository<Result, Integer> {

//	List<Result> findByQuiz(Quiz quiz);

	List<Result> findByUserId(long user_id);

	List<Result> findByQuizIdAndUserId(long quizId, long userId);
	List<Result> findByQuizId(long quizId);
	List<Result> findByBatchId(long batchId);

	boolean existsByQuizId(long quizId);
	boolean existsByBatchId(long batchId);
	void deleteByBatchId(long batchId);

}
