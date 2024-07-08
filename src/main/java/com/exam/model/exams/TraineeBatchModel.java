package com.exam.model.exams;

import com.exam.entity.Batch;
import com.exam.entity.Question;
import com.exam.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TraineeBatchModel {

    private  long quizId;
    private  String name;
    private  String description;
    private  boolean active=false;
    private String markOfQuestion;
    private String maxMarks;
    private  String noOfQuestions;
    private String quizTime;
    private Set<Question> questionsOfQuiz;
    private long batchId;
    private String batchName;
    private LocalDate date;
    private LocalTime time;
    private Integer traineeCount;
	private String status;
  private String batchStatus;
  private String batchUserStatus;
  private String trade;
  private boolean negativeMarks;




  public  TraineeBatchModel(QuizModel quiz, Batch batch ){
      this.quizId=quiz.getQuizId();
      this.name=quiz.getName();
      this.description =quiz.getDescription();
      this.markOfQuestion =quiz.getMarkOfQuestion();
      this.maxMarks=quiz.getMaxMarks();
      this.noOfQuestions=quiz.getNoOfQuestions();
      this.quizTime=quiz.getQuizTime();
      this.active=quiz.isActive();
      this.questionsOfQuiz=quiz.getQuestionsOfQuiz();
      this.batchId=batch.getId();
      this.batchName=batch.getName();
      this.date=batch.getDate();
      this.time=batch.getTime();
      this.traineeCount=batch.getTraineeCount();
//      this.batchStatus=batch.getStatus();
      this.trade=batch.getTrade();
      this.negativeMarks=quiz.isNegativeMarks();
    }
}
