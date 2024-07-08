package com.exam.services.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.exam.entity.Quiz;
import com.exam.model.exams.StudentResult;
import com.exam.model.exams.UserResultModel;
import com.exam.repo.BatchRepository;
import com.exam.repo.QuizRepository;
import com.exam.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.exam.entity.User;
import com.exam.entity.Result;
import com.exam.repo.ResultRepository;
import com.exam.services.ResultService;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class ResultServiceImpl implements ResultService {
	
	@Autowired
	private ResultRepository resultrepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private QuizRepository quizRepository;

	@Override
	public Result addResult(Result result) {
		return this.resultrepository.save(result);
	}

	@Override
	public Result addQuizResult(long qid, Result result, long batchId) {
		System.out.println(batchId+"type :");

		result.getSolvedQuestion().forEach(q->{
			q.setQuizId(qid);
			q.setBatchId(batchId);
		});

		result.setBatchId(batchId);

		return resultrepository.save(result);
	}


	@Override
	public List<Result> getAllResult() {
		// TODO Auto-generated method stub
		return this.resultrepository.findAll();
	}
	@Override
	public List<UserResultModel> getAllResultsByQuiz(long quizId){

		List<Result> results=resultrepository.findByQuizId(quizId);
		List<UserResultModel> userResultModels=new ArrayList<>();
		results.forEach(result->{
			Optional<User> user =userRepository.findById(result.getUserId());;
					//findById(result.getUserId());
			if(user.isPresent()) {
			UserResultModel model=new UserResultModel(user,result);
			userResultModels.add(model);}
		});
	return  userResultModels;
    }

	@Override
	public List<UserResultModel> getAllResultsByBatch(long batchId) {

		List<Result> results=resultrepository.findByBatchId(batchId);
		List<UserResultModel> userResultModels=new ArrayList<>();
		results.forEach(result->{
			Optional<User> user =userRepository.findById(result.getUserId());;
					//findById(result.getUserId());
			if(user.isPresent()) {
				UserResultModel model = new UserResultModel(user, result);
				userResultModels.add(model);
			}
		});
	return  userResultModels;
	}

	@Override
	public List<Result> getResultOfQuiz(Long quizId) {
//		List<Result> results=resultrepository.findByQuizId(quizId);
//		List<QuizResultModel> resultModels=new ArrayList<>();
//		results.forEach(result->{
//			QuizResultModel model=new QuizResultModel();
//			Optional<User> u=userRepository.findById(result.getUserId());
//			model.setUser(u);
//		});
//
		return null;
	}



//	@Override
//	public List<Result> getResultOfQuiz(Quiz quiz) {
//		return this.resultrepository.findByQuiz(quiz);
//	}

	@Override
	public List<Result> getResultOfUser(long userId) {
		// TODO Auto-generated method stub
		return this.resultrepository.findByUserId(userId);
	}

	@Override
	public List<Result> getResultOfUser(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Result> findByQuizIdAndUserId(long quizId, long userId) {
		// TODO Auto-generated method stub
		return this.resultrepository.findByQuizIdAndUserId(quizId, userId);
	}

	@Override
	public List<StudentResult> findByUser(long userId) {
		List<Result> results=resultrepository.findByUserId(userId);
		List<StudentResult> studentResults=new ArrayList<>();

        for (Result result : results) {
            StudentResult studentResult = new StudentResult();

            studentResult.setResult_id(result.getResult_id());
            studentResult.setQuizId(result.getQuizId());
            studentResult.setBatchId(result.getBatchId());
            studentResult.setQAttempted(result.getqAttempted());
            studentResult.setCorrectAns(result.getCorrectAns());
            studentResult.setMarksScored(result.getMarksScored());
            studentResult.setSubmitDateTime(result.getSubmitDateTime());

            Quiz quiz = quizRepository.getById(result.getQuizId());

            studentResult.setQuizName(quiz.getName());
            studentResult.setTrade(quiz.getTrade());
            studentResult.setNoOfQuestions(quiz.getNoOfQuestions());
            studentResult.setNegativeMarks(quiz.isNegativeMarks());

            studentResults.add(studentResult);
        }

        return studentResults;


	}

	@Transactional
	public void deleteResultByBatchId(long batchId) {
		resultrepository.deleteByBatchId(batchId);
	}

	public boolean doesResultExistForQuiz(long quizId) {
		return resultrepository.existsByQuizId(quizId);
	}

	public boolean doesResultExistForBatch(long batchId) {
		return resultrepository.existsByBatchId(batchId);
	}

//	@Override
//	public List<Result> getResultOfUserAndQuiz(Quiz quiz, User user) {
//		// TODO Auto-generated method stub
//		return this.resultrepository.findByQuizAndUser(quiz,user);
//	}

}
