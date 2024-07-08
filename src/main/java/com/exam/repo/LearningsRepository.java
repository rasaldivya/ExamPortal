package com.exam.repo;

import com.exam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Learnings;

import java.util.List;

public interface LearningsRepository extends JpaRepository<Learnings, Long>{

    List<Learnings> findAllByOrderByCreatedAt();
    List<Learnings> findByTradeOrderByCreatedAtDesc(String trade);
}
