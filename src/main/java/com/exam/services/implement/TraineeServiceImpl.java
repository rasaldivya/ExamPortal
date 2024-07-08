package com.exam.services.implement;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.exam.model.exams.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.exam.helper.TraineeHelper;
import com.exam.entity.User;
import com.exam.repo.TraineeRepository;
import com.exam.services.TraineeService;
@Repository
public class TraineeServiceImpl implements TraineeService {

	@Autowired
	TraineeRepository traineeRepo;
	@Autowired
	TraineeHelper helper;
	
	@Override
	public User addTrainee(User trainee) {
		// TODO Auto-generated method stub
		return traineeRepo.save(trainee);
	}
	@Override
    public Set<UserModel> getAllTrainees() {
        Set<User> users=new HashSet<>();
        Set<UserModel> trainees=new HashSet<>();
        users=new LinkedHashSet<>(this.traineeRepo.findAll());
        users.forEach(user->{
            UserModel model=new UserModel();
            BeanUtils.copyProperties(user,model);
            trainees.add(model);
        });
        return trainees;
    }

    @Override
    public User getTrainee(long id) {
        return this.traineeRepo.findById(id).get();
    }
    @Override
    public void deleteTrainee(long id) {
        this.traineeRepo.deleteById(id);
    }
    @Override
    public User updateTrainee(User trainee) {
        return this.traineeRepo.save(trainee);
    }
    
    @Override
    public List<User> uploadTrainee(MultipartFile file) throws IOException {
    	System.out.println("File for upload"+file);
    	try {
            List<User> trainees = helper.convertExcelToListOfProduct(file.getInputStream());
            return this.traineeRepo.saveAll(trainees);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
	@Override
	public List<User> findByRole(String role) {
		// TODO Auto-generated method stub
		return traineeRepo.findByRole(role);
	}

	
}
