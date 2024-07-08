package com.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long quesId;
    private String type;
    private String topic;
	@Column(length = 2000)
    private  String content;
    private  String image;
    private  String option1;
    private  String option2;
    private  String option3;
    private  String option4;
    private String trade;

    private  String answer;
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;


//    @OneToMany(cascade = CascadeType.ALL)
//    private  Set<Quiz> quiz;
//

//	public Question() {
//	}
//
//
//	public Question(long quesId, String type, String topic, String content, String image, String option1,
//			String option2, String option3, String option4, String answer,Set<Quiz> quiz) {
//		super();
//		this.quesId = quesId;
//		this.type = type;
//		this.topic = topic;
//		this.content = content;
//		this.image = image;
//		this.option1 = option1;
//		this.option2 = option2;
//		this.option3 = option3;
//		this.option4 = option4;
//		this.answer = answer;
////		this.quiz = quiz;
//	}
//
//
//	public long getQuesId() {
//		return quesId;
//	}
//
//
//	public void setQuesId(long quesId) {
//		this.quesId = quesId;
//	}
//
//
//	public String getType() {
//		return type;
//	}
//
//
//	public void setType(String type) {
//		this.type = type;
//	}
//
//
//	public String getTopic() {
//		return topic;
//	}
//
//
//	public void setTopic(String topic) {
//		this.topic = topic;
//	}
//
//
//	public String getContent() {
//		return content;
//	}
//
//
//	public void setContent(String content) {
//		this.content = content;
//	}
//
//
//	public String getImage() {
//		return image;
//	}
//
//
//	public void setImage(String image) {
//		this.image = image;
//	}
//
//
//	public String getOption1() {
//		return option1;
//	}
//
//
//	public void setOption1(String option1) {
//		this.option1 = option1;
//	}
//
//
//	public String getOption2() {
//		return option2;
//	}
//
//
//	public void setOption2(String option2) {
//		this.option2 = option2;
//	}
//
//
//	public String getOption3() {
//		return option3;
//	}
//
//
//	public void setOption3(String option3) {
//		this.option3 = option3;
//	}
//
//
//	public String getOption4() {
//		return option4;
//	}
//
//
//	public void setOption4(String option4) {
//		this.option4 = option4;
//	}
//
//
//	public String getAnswer() {
//		return answer;
//	}
//
//
//	public void setAnswer(String answer) {
//		this.answer = answer;
//	}


//	public Set<Quiz> getQuiz() {
//		return quiz;
//	}
//
//
//	public void setQuiz(Set<Quiz> quiz) {
//		this.quiz = quiz;
//	}


//	@Override
//	public String toString() {
//		return "Question [quesId=" + quesId + ", type=" + type + ", topic=" + topic + ", content=" + content
//				+ ", image=" + image + ", option1=" + option1 + ", option2=" + option2 + ", option3=" + option3
//				+ ", option4=" + option4 + ", answer=" + answer + "]";
//	}

    
}
