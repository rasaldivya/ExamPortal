package com.exam.services;

import com.exam.entity.User;
import com.exam.model.exams.UserModel;

import java.util.List;
import java.util.Set;

public interface UserService {
    public UserModel addTrainee(UserModel user) throws Exception;
    public User addUser(User user) throws Exception;
    public User updateUser(User user);
    public User getUser(String uname);
    public User getUserById(Long userId);
    public List<User> findByRole(String role);

    public User getUserByArmyNo(String armyNo);
    public void deleteUser(Long userId);

    public void updateTrade(Long userId, String trade);

    public void updateSquad(Long userId, String squad);
    public List<User> getUsersByTradeAndSquadAndRoleOrderByCreatedAt(String trade, String squad, String role);

    public Set<String> getAllUniqueSquads();
    public Set<String> getAllUniqueSquadsFilteredByTrade(String trade);
    public Set<String> getAllUniqueTrades();

    void saveUsers(List<User> userDTOs);
}
