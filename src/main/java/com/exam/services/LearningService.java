package com.exam.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.entity.Learnings;

@Service
public interface LearningService {
	public Learnings addLearnings(Learnings learning);

	public List<Learnings> getAllLearnings(String trade);

	public Learnings getLearning(long id);

	public void deletelearning(long id);

	public Learnings updateLearning(Learnings learning);

}
