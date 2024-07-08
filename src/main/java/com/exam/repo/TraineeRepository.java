package com.exam.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.User;

public interface TraineeRepository extends JpaRepository<User, Long> {

	List<User> findByRole(String role);

}
