package com.exam.model.exams;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.exam.entity.Question;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class QuizModel {

		private  long quizId;
	    private  String name;
	    private  String description;
	    private  boolean active=false;
	    private String markOfQuestion;
	    private String maxMarks;
	    private  String noOfQuestions;
	    private String quizTime;
		private boolean negativeMarks;
		private String trade;
		private String createdAt;
		private String updatedAt;
		private Set<Question> questionsOfQuiz;
	    private List<BatchModel> batches;


}
