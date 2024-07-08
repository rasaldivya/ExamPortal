package com.exam.services;

import com.exam.entity.Question;
import com.exam.entity.Quiz;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public interface QuestionService {
    public Question addQuestion(Question question);
    public Question updateQuestion(Question question);
    public Set<Question> getQuestions();
    public Question getQuestion(Long questionId);
    public  Set<Question> getQuestionOfQuiz(Quiz quiz);
    public void deleteQuestion(Long qid);
	public void uploadQuestions(MultipartFile file,String trade);
    public void saveQuestions(List<Question> questions);

}
