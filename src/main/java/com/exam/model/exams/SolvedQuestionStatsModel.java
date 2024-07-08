package com.exam.model.exams;

import com.exam.entity.SolvedQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SolvedQuestionStatsModel {
    private long questionId;
    private long attemptCount;
    private double correctAnswerPercentage;
    private  long correctAnswerCount;
    private long changedAnswerCount;
    private long reviewAnswerCount;
    private String type;
    private String topic;
    private String content;
    private String image;
    private String trade;


    public SolvedQuestionStatsModel(long questionId, long attemptCount, double correctAnswerPercentage, long correctAnswerCount, SolvedQuestion solvedQuestion, long changedAnswerCount,long reviewAnswerCount) {

        this.questionId=questionId;
        this.attemptCount=attemptCount;
        this.correctAnswerPercentage=correctAnswerPercentage;
        this.correctAnswerCount=correctAnswerCount;
        this.changedAnswerCount=changedAnswerCount;
        this.reviewAnswerCount=reviewAnswerCount;
        BeanUtils.copyProperties(solvedQuestion, this);

    }

}
