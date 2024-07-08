package com.exam.services.implement;

import com.exam.helper.QuestionHelper;
import com.exam.entity.Question;
import com.exam.entity.Quiz;
import com.exam.repo.QuestionRepository;
import com.exam.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Repository
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question addQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Set<Question> getQuestions() {
        return new LinkedHashSet<>(this.questionRepository.findAll());
    }

    @Override
    public Question getQuestion(Long questionId) {
        return this.questionRepository.findById(questionId).get();
    }

    @Override
    public Set<Question> getQuestionOfQuiz(Quiz quiz) {
//        return this.questionRepository.findByQuiz(quiz);
        return null;
    }

    @Override
    public void deleteQuestion(Long qid) {
        this.questionRepository.deleteById(qid);
    }

	@Override
	public void uploadQuestions(MultipartFile file,String trade){
		System.out.println("File for upload"+file);
    	try {
            List<Question> questions = QuestionHelper.convertExcelToListOfProduct(file.getInputStream(),trade);
           System.out.println("Questions from list : "+questions);
            this.questionRepository.saveAll(questions);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

    @Override
    public void saveQuestions(List<Question> questions) {
       this.questionRepository.saveAll(questions);
    }
}
