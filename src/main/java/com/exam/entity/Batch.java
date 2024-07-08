package com.exam.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private LocalDate date;
	private LocalTime time;
    private Integer traineeCount;
//	private boolean status;
    private String trade;
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@UpdateTimestamp
	@Column(name = "updated_at", nullable = false)
	private Date updatedAt;
	 @ManyToOne
	 @JoinColumn(name = "quiz_id")
	 private Quiz quiz;

//	@OneToMany(mappedBy = "batch")
//	private List<QuizBatchMapping> quizBatchMappings;

	@OneToMany(mappedBy = "batch")
	private List<BatchUserMapping> batchUserMappings;

}
