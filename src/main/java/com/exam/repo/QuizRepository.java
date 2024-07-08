package com.exam.repo;

import com.exam.entity.Quiz;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz,Long>{

//	public List<Quiz> findByCategory(Category cat);
List<Quiz> findAllByOrderByCreatedAtDesc();
List<Quiz> findByTradeOrderByCreatedAtDesc(Optional<String> trade);
	

}
