package com.exam.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
@Data
public class Result {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long result_id;

	private long quizId;
	private long userId;
	private int qAttempted;
	private int correctAns;
	private double marksScored;
	private String submitDateTime;
	private long batchId;

	@OneToMany(cascade = CascadeType.ALL)
	private Set<SolvedQuestion> solvedQuestion;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;



	public Result() {
		super();
	}

	public Result(long result_id, long quizId, long userId, int qAttempted, int correctAns, double marksScored,
			String submitDateTime,long batchId, Set<SolvedQuestion> solvedQuestion) {
		super();
		this.result_id = result_id;
		this.quizId = quizId;
		this.userId = userId;
		this.qAttempted = qAttempted;
		this.correctAns = correctAns;
		this.marksScored = marksScored;
		this.submitDateTime = submitDateTime;
		this.solvedQuestion = solvedQuestion;
		this.batchId=batchId;

	}

	public long getResult_id() {
		return result_id;
	}

	public void setResult_id(long result_id) {
		this.result_id = result_id;
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

	public long getBatchId(){return batchId;}

	public void setBatchId(long batchId){this.batchId=batchId;}

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
		return "Result [result_id=" + result_id + ", quizId=" + quizId + ", userId=" + userId + ", qAttempted="
				+ qAttempted + ", correctAns=" + correctAns + ", marksScored=" + marksScored + ", submitDateTime="
				+ submitDateTime + ", solvedQuestion=" + solvedQuestion + " batchId=" + batchId +"]";
	}


	
	

}
