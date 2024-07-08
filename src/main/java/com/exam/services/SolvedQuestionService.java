package com.exam.services;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.exam.entity.SolvedQuestion;
import com.exam.model.exams.SolvedQuestionStatsModel;
import com.exam.repo.SolvedQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class SolvedQuestionService {

    @Autowired
    private SolvedQuestionRepository solvedQuestionRepository;

    public List<SolvedQuestionStatsModel> getQuestionStats(Long quizId,Long batchId) {
        List<SolvedQuestion> solvedQuestions=new ArrayList<>();
        if(quizId!=null&&batchId!=null) {
            solvedQuestions = solvedQuestionRepository.findByQuizIdAndBatchId(quizId, batchId);
        }
        else if(quizId!=null){
            solvedQuestions = solvedQuestionRepository.findByQuizId(quizId);
        }
        else if(batchId!=null){
            solvedQuestions = solvedQuestionRepository.findByBatchId(batchId);
        }
        else {
            solvedQuestions = solvedQuestionRepository.findAll();
        }
        return solvedQuestions.stream()
                .collect(Collectors.groupingBy(SolvedQuestion::getQuesId))
                .entrySet().stream()
                .map(entry -> {
                    long questionId = entry.getKey();
                    long attemptCount = entry.getValue().size();
                    SolvedQuestion solvedQuestion=entry.getValue().stream().findFirst().get();
                    long correctAnswerCount = entry.getValue().stream()
                            .filter(sq -> sq.getAnswer().equals(sq.getGivenAnswer()))
                            .count();
                    long changedAnswerCount = entry.getValue().stream()
                            .filter(sq -> sq.getChangedAnswer()!=null)
                            .count();
                    long reviewAnswerCount = entry.getValue().stream()
                            .filter(sq -> sq.getReview()!=null)
                            .count();
                    double correctAnswerPercentage = (double) correctAnswerCount / attemptCount * 100;

                    return new SolvedQuestionStatsModel(questionId, attemptCount, correctAnswerPercentage,correctAnswerCount,solvedQuestion,changedAnswerCount,reviewAnswerCount);
                })
                .collect(Collectors.toList());
    }
}
