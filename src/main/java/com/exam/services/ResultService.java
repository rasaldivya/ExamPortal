package com.exam.services;

import java.util.List;

import com.exam.entity.User;
import com.exam.entity.Result;
import com.exam.model.exams.StudentResult;
import com.exam.model.exams.UserResultModel;
import org.springframework.transaction.annotation.Transactional;

public interface ResultService {
	
	public Result addResult(Result result);
	public List<Result> getAllResult();
	public List<Result> getResultOfQuiz(Long quizId);
	public List<Result> getResultOfUser(User user);
//	public List<Result> getResultOfUserAndQuiz(Quiz quiz,User user);
	public List<Result> getResultOfUser(long userId);
	public Result addQuizResult(long qid, Result result, long batchId);
	public List<Result> findByQuizIdAndUserId(long quizId, long userId);
	public List<StudentResult> findByUser(long userId);
	@Transactional
	public void deleteResultByBatchId(long batchId);

	public List<UserResultModel> getAllResultsByQuiz(long quizId);
	public List<UserResultModel> getAllResultsByBatch(long batchId);

}
