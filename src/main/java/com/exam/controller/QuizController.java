package com.exam.controller;

import com.exam.entity.Quiz;
import com.exam.model.exams.QuizModel;
import com.exam.services.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/quiz")
@CrossOrigin("*")
public class QuizController {

    @Autowired
    private QuizService quizService;

    //add quiz
    @PostMapping("/")
    public ResponseEntity<?> addQuiz(@RequestBody QuizModel quiz)
    {

        System.out.println("Quiz to add"+quiz);
        return ResponseEntity.ok(this.quizService.addQuiz(quiz));
    }
    //update quiz
    @PutMapping("/update/{quizId}")
    public ResponseEntity<?> updateQuiz(@PathVariable Long quizId, @RequestBody Quiz updatedQuiz) {
        try {
            return ResponseEntity.ok().body(quizService.updateQuiz(quizId, updatedQuiz));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/update/status/{quizId}")
    public ResponseEntity<?> updateQuizStatus(@PathVariable Long quizId, @RequestBody boolean status) {
        try {
            return ResponseEntity.ok().body(quizService.updateQuizStatus(quizId, status));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    //get quiz
    @GetMapping("/")
    public ResponseEntity<?> getQuizzes( @RequestParam(name="trade",required = false) String trade)
    {
        System.out.println("Quizes for trade : "+trade);
        return ResponseEntity.ok(this.quizService.getQuizzes(trade));
    }
    @GetMapping("/trainee")
    public ResponseEntity<?> getQuizzesTrainee()
    {

        return ResponseEntity.ok(this.quizService.getQuizzesTrainee());
    }

    @GetMapping("/trainee/batch")
    public ResponseEntity<?> getTraineeBatchModel() {
        return ResponseEntity.ok(this.quizService.getTraineeBatchModel());
    }

    //get single quiz	
    @GetMapping("{qid}")
    public QuizModel getSingleQuiz(@PathVariable("qid") Long qid)
    {


        return this.quizService.getQuiz(qid);
    }
    //delete Quiz
    @DeleteMapping("/{qid}")
    public ResponseEntity<?> deleteQuiz(@PathVariable("qid") Long qid)
    {
        System.out.println("Delteing quiz"+qid);
        try {
            this.quizService.deleteQuiz(qid);
            return ResponseEntity.ok("{\"status\":\"Success\"}");
        
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    
    //get quiz of particular category
//    @GetMapping("/category/{cid}")
//    public List<Quiz> getQuizOfCategory(@PathVariable("cid") Long cid)
//    {
//    	Category cat=new Category();
//    	cat.setCid(cid);
//    	return (this.quizService.getQuizzesOfCategory(cat));
//    	
//    }
    @PostMapping("/demo")
    public ResponseEntity<?> demo(@RequestBody @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date)
    {

        System.out.println("Selected date : "+date.plusDays(1));
        return null;
    }
}
