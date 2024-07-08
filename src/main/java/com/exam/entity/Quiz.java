package com.exam.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long quizId;
    private  String name;
    private  String description;
    private  boolean active;
    private String markOfQuestion;
    private String maxMarks;
    private String noOfQuestions;
    private String quizTime;
    private String questionsOfQuiz;
    private boolean negativeMarks;
    private String trade;
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

//    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
//    private List<Batch> batches;

    @OneToMany(mappedBy = "quiz")
    private List<QuizBatchMapping> quizBatchMappings;

	@Override
	public String toString() {
		return "Quiz [quizId=" + quizId + ", name=" + name + ", description=" + description + ", active=" + active
				+ ", markOfQuestion=" + markOfQuestion + ", maxMarks=" + maxMarks + ", noOfQuestions=" + noOfQuestions
				+ ", quizTime=" + quizTime + ", questionsOfQuiz=" + questionsOfQuiz + "]";
	}

	
   
}
