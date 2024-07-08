package com.exam.model.exams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StudentResult {
    private long result_id;
    private long quizId;
    private long batchId;
    private int qAttempted;
    private int correctAns;
    private double marksScored;
    private String submitDateTime;
    private String quizName;
    private String noOfQuestions;
    private String trade;
    private boolean negativeMarks;
}
