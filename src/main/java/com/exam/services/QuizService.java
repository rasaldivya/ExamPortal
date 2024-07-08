package com.exam.services;

import com.exam.entity.Quiz;
import com.exam.model.exams.QuizModel;
import com.exam.model.exams.TraineeBatchModel;

import java.util.List;

public interface QuizService {
    public Quiz addQuiz(QuizModel quiz);
    public QuizModel updateQuiz(Long quizId, Quiz updatedQuiz);
    QuizModel updateQuizStatus(Long quizId, boolean status);
    public List<QuizModel> getQuizzes(String trade);
    public List<QuizModel> getQuizzesTrainee();

    public List<TraineeBatchModel> getTraineeBatchModel();
    public  QuizModel getQuiz(Long quizId);
    public  void  deleteQuiz(Long quizId) throws Exception;
//	public List<Quiz> getQuizzesOfCategory(Category cat);
//	Quiz addQuizResult(long qid, Result result);

}
