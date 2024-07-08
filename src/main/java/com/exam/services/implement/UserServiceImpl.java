package com.exam.services.implement;

import com.exam.exceptions.NotFoundException;
import com.exam.model.exams.UserModel;
import com.exam.repo.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.exam.entity.User;
import com.exam.services.UserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private String defaultPassword="IndArmy@2024";

//    @Autowired
//    private com.exam.repo.RoleRepository roleRepository;
    @Override
    public UserModel addTrainee(UserModel userModel) throws Exception {

        User local =this.userRepository.findByuserName(userModel.getUsername());

        if(local !=null)
        {
            System.out.println("User already present");
            throw  new Exception("User already present");
        }
        else
        {
        	userModel.setRole("NORMAL");
            User user=new User();
            BeanUtils.copyProperties(userModel,user);
            local=this.userRepository.save(user);
            //userRole.setUser(local);
        }
        UserModel userSaved=new UserModel();
        BeanUtils.copyProperties(local,userSaved);

        return userSaved;
    }
    @Override
    public User addUser(User user) throws Exception {

        User local =this.userRepository.findByuserName(user.getUsername());

        if(local !=null)
        {
            System.out.println("User already present");
            throw  new Exception("User already present");
        }
        else
        {
        	user.setRole("ADMIN");
            local=this.userRepository.save(user);
            //userRole.setUser(local);
        }
        return local;
    }
    @Override
    public User updateUser(User user){
            user.setRole("ADMIN");
        return this.userRepository.save(user);
    }

    @Override
    public User getUser(String uname) {
        return this.userRepository.findByuserName(uname);
    }
    @Override
    public List<User> findByRole(String role) {
        // TODO Auto-generated method stub
        return userRepository.findByRole(role);
    }
    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

	@Override
	public User getUserByArmyNo(String armyNo) {
		// TODO Auto-generated method stub
		return userRepository.findByArmyNo(armyNo);
	}

    public User getUserById(Long userId){
        return userRepository.findById(userId).get();
    }

    public void updateTrade(Long userId, String trade) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setTrade(trade);
        userRepository.save(user);
    }

    public void updateSquad(Long userId, String squad) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        user.setSquad(squad);
        userRepository.save(user);
    }

    public List<User> getUsersByTradeAndSquadAndRoleOrderByCreatedAt(String trade, String squad, String role) {
        if (trade != null && squad != null && role != null) {
            return userRepository.findByTradeAndSquadAndRoleOrderByCreatedAtDesc(trade, squad, role);
        } else if (trade != null && squad != null) {
            return userRepository.findByTradeAndSquadOrderByCreatedAtDesc(trade, squad);
        } else if (trade != null && role != null) {
            return userRepository.findByTradeAndRoleOrderByCreatedAtDesc(trade, role);
        } else if (squad != null && role != null) {
            return userRepository.findBySquadAndRoleOrderByCreatedAtDesc(squad, role);
        } else if (trade != null) {
            return userRepository.findByTradeOrderByCreatedAtDesc(trade);
        } else if (squad != null) {
            return userRepository.findBySquadOrderByCreatedAtDesc(squad);
        } else if (role != null) {
            return userRepository.findByRoleOrderByCreatedAtDesc(role);
        } else {
            return userRepository.findAllByOrderByCreatedAt();
        }
    }

    public Set<String> getAllUniqueSquads() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(User::getSquad)
                .collect(Collectors.toSet());
    }
    public Set<String> getAllUniqueSquadsFilteredByTrade(String trade) {
        List<User> users;
        if (trade != null) {
            users = userRepository.findByTradeOrderByCreatedAtDesc(trade);
        } else {
            users = userRepository.findAll();
        }
        return users.stream()
                .map(User::getSquad)
                .collect(Collectors.toSet());
    }
    public Set<String> getAllUniqueTrades() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(User::getTrade)
                .collect(Collectors.toSet());
    }

    @Transactional
    public void saveUsers(List<User> userDTOs) {
        List<User> users = userDTOs.stream()
                .map(userDTO -> {
                    User user = new User();
                    BeanUtils.copyProperties(userDTO,user);
                    user.setUserName(userDTO.getArmyNo());
                    user.setPassword(bCryptPasswordEncoder.encode(defaultPassword));
                    user.setRole("NORMAL");
                    user.setProfile("default.png");
                    return user;
                })
                .collect(Collectors.toList());

        userRepository.saveAll(users);
    }
}
