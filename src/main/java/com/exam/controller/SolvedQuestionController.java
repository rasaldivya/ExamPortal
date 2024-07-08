package com.exam.controller;

import com.exam.model.exams.SolvedQuestionStatsModel;
import com.exam.services.SolvedQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/solved-questions")
public class SolvedQuestionController {

    @Autowired
    private SolvedQuestionService solvedQuestionService;

    @GetMapping("/stats")
    public List<SolvedQuestionStatsModel> getQuestionStats(@RequestParam(name = "quizId",required = false) Long quizId,
                                                           @RequestParam(name = "batchId",required = false) Long batchId) {

        System.out.println("Quiz Id : "+quizId);
        System.out.println("Batch Id : "+batchId);
        return solvedQuestionService.getQuestionStats(quizId,batchId);
    }
}

