package com.exam.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.exam.entity.Learnings;
import com.exam.services.LearningService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/learnings")
@CrossOrigin("*")

public class LearningsController {
	
	@Autowired
	private LearningService learningService;
	
	 @PostMapping("/")
	 public ResponseEntity<?> addLearnings(@RequestBody Learnings learning)
	 {
	   	System.out.println("This learning need to add => "+learning.toString());
	   	return ResponseEntity.ok(learningService.addLearnings(learning));
    }

	//get all Trainees
	    @GetMapping("/")
	    public ResponseEntity<?> getAllLearnings(@RequestParam(required = false) String trade)
	    {
	    	System.out.println("get all notes controller");
	      
	      List<Learnings>list=this.learningService.getAllLearnings(trade);

	        return ResponseEntity.ok(list);
	    }
	    
	    @GetMapping("/{tId}")
	    public ResponseEntity<?> getLearning(@PathVariable("tId") int tId)
	    {
	    	System.out.println(learningService.getLearning(tId));
	        return ResponseEntity.ok(this.learningService.getLearning(tId));
	    }
	    
	    @RequestMapping(method = RequestMethod.PUT,path = "/")
	    public ResponseEntity<?> updateEntity(@RequestBody Learnings learning)
	    {
	    	System.out.println("Update note controller");

	        return ResponseEntity.ok(this.learningService.updateLearning(learning));
	    }
	    //delete a single Trainee
	    @DeleteMapping("{id}")
	    public ResponseEntity<?> deleteLearning(@PathVariable("id") int id)
	    {
	    	System.out.println("delete note controller");
			try {
				this.learningService.deletelearning(id);
				return ResponseEntity.ok("Success");
			}
			catch (Exception e) {
				return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
			}
	    }
	@PostMapping("/upload-file")
	public ResponseEntity<?> uploadLearningFile(@RequestParam("file") MultipartFile file,
												@RequestParam("trade") String trade)
	{
		System.out.println("file Type...."+file.getContentType());
		System.out.println("file Name...."+file.getOriginalFilename());
		try {
			//file.transferTo(new File("D:\\demo\\examPortalAngularFrontEnd-master\\src\\assets\\notes\\"+trade+"\\"+file.getOriginalFilename()));
//			For Deployment
			file.transferTo(new File("C:\\exam\\frontend\\nginx\\html\\exam-front\\assets\\notes\\"+trade+"\\"+file.getOriginalFilename()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


		return ResponseEntity.ok("Success");
	}
}
