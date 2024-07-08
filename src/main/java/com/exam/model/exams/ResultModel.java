package com.exam.model.exams;

import com.exam.entity.SolvedQuestion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Set;


@Data
//@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class ResultModel {
	private long quizId;
	private long userId;
	private int qAttempted;
	private int correctAns;
	private double marksScored;
	private String submitDateTime;
	private Set<SolvedQuestion> solvedQuestion;
	private long batchId;
	public ResultModel() {
		super();
	}
	public ResultModel(long quizId, long userId, int qAttempted, int correctAns, double marksScored,
			String submitDateTime, Set<SolvedQuestion> solvedQuestion,long batchId) {
		super();
		this.quizId = quizId;
		this.userId = userId;
		this.qAttempted = qAttempted;
		this.correctAns = correctAns;
		this.marksScored = marksScored;
		this.submitDateTime = submitDateTime;
		this.solvedQuestion = solvedQuestion;
		this.batchId=batchId;
	}
	
	public long getQuizId() {
		return quizId;
	}
	public void setQuizId(long quizId) {
		this.quizId = quizId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public int getqAttempted() {
		return qAttempted;
	}
	public void setqAttempted(int qAttempted) {
		this.qAttempted = qAttempted;
	}
	public int getCorrectAns() {
		return correctAns;
	}
	public void setCorrectAns(int correctAns) {
		this.correctAns = correctAns;
	}
	public double getMarksScored() {
		return marksScored;
	}
	public void setMarksScored(double marksScored) {
		this.marksScored = marksScored;
	}
	public String getSubmitDateTime() {
		return submitDateTime;
	}
	public void setSubmitDateTime(String submitDateTime) {
		this.submitDateTime = submitDateTime;
	}
	public Set<SolvedQuestion> getSolvedQuestion() {
		return solvedQuestion;
	}
	public void setSolvedQuestion(Set<SolvedQuestion> solvedQuestion) {
		this.solvedQuestion = solvedQuestion;
	}
	@Override
	public String toString() {
		return "ResultModel [quizId=" + quizId + ", userId=" + userId + ", qAttempted="
				+ qAttempted + ", correctAns=" + correctAns + ", marksScored=" + marksScored + ", submitDateTime="
				+ submitDateTime + ", solvedQuestion=" + solvedQuestion + "]";
	}
	
	

}
