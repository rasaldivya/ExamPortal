package com.exam.controller;

import java.util.List;

import com.exam.model.exams.StudentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.entity.Result;
import com.exam.services.QuizService;
import com.exam.services.ResultService;

@RestController
@RequestMapping("/result")
@CrossOrigin("*")
public class ResultController {
	
	@Autowired
	private ResultService resultservice;
	@Autowired
	private QuizService quizService;
	
	@PostMapping("/{qid}/batch/{batchId}")
	public ResponseEntity<?> addResult(@PathVariable("qid") long qid,@PathVariable("batchId") long batchId,@RequestBody Result result)
	{
		

		return ResponseEntity.ok(this.resultservice.addQuizResult(qid, result,batchId));
//		return null;
	}
	@GetMapping("/{qid}")
	public ResponseEntity<?> getResultOfQuiz(@PathVariable("qid") long qid)
	{

		System.out.println("Get result of quiz Id" +qid);
		return ResponseEntity.ok(this.resultservice.getResultOfQuiz(qid));
//		return null;
	}
	
	@GetMapping("/solved/{qid}/{uid}")
	public ResponseEntity<?> getResultByUserAndQuiz(@PathVariable("qid") long qid,@PathVariable("uid") long uid)
	{
		List<Result> result=(this.resultservice.findByQuizIdAndUserId(qid, uid));
		return ResponseEntity.ok(result);
		
	}
	@GetMapping("/solved/trainee/{uid}")
	public ResponseEntity<?> getResultByUser(@PathVariable("uid") long uid)
	{
		resultservice.findByUser(uid);
		List<StudentResult> result=resultservice.findByUser(uid);
		return ResponseEntity.ok(result);
//		return null;

	}
	
	@GetMapping("/quiz/{qid}")
	public ResponseEntity<?> getResultByQuiz(@PathVariable("qid") long qid)
	{
		
		return ResponseEntity.ok(this.resultservice.getAllResultsByQuiz(qid));

	}	
	@GetMapping("/batch/{batchId}")
	public ResponseEntity<?> getResultByBatch(@PathVariable("batchId") long batchId)
	{
		
		return ResponseEntity.ok(this.resultservice.getAllResultsByBatch(batchId));

	}
	
	
	

}
