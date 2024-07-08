package com.exam.services.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.exam.entity.Learnings;
import com.exam.repo.LearningsRepository;
import com.exam.services.LearningService;

@Repository
public class LearningServiceImpl implements LearningService {

	@Autowired
	private LearningsRepository learningRepo;
	
	@Override
	public Learnings addLearnings(Learnings learning) {
		// TODO Auto-generated method stub
		return learningRepo.save(learning);
	}

	@Override
	public List<Learnings> getAllLearnings(String trade) {
		// TODO Auto-generated method stub
		if(trade!=null)
			return learningRepo.findByTradeOrderByCreatedAtDesc(trade);
		else
			return learningRepo.findAllByOrderByCreatedAt();
	}

	@Override
	public Learnings getLearning(long id) {
		// TODO Auto-generated method stub
		return learningRepo.findById(id).get();
	}

	@Override
	public void deletelearning(long id) {
		// TODO Auto-generated method stub
		learningRepo.deleteById(id);
	}

	@Override
	public Learnings updateLearning(Learnings learning) {
		// TODO Auto-generated method stub
		return learningRepo.save(learning);
	}

}
