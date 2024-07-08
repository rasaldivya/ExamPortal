package com.exam.services;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.exam.model.exams.UserModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.exam.entity.User;

@Service
public interface TraineeService {

	public User addTrainee(User trainee);
	public Set<UserModel> getAllTrainees();
	public User getTrainee(long id);
	public void deleteTrainee(long id);
	public User updateTrainee(User trainee);
	public List<User> findByRole(String role);

	List<User> uploadTrainee(MultipartFile file) throws IOException;
}
