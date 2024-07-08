package com.exam.model.exams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultModel {
    private UserModel user;
    private int qAttempted;
    private int correctAns;
    private double marksScored;
}
