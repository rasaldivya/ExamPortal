package com.exam.repo;

import com.exam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {


    User findByuserName(String userName);
    User findByArmyNo(String armyNo);
    List<User> findAllByOrderByCreatedAt();
    List<User> findByRole(String role);

//    User findByUserId(Long id);
boolean existsByArmyNo(String armyNo);

    List<User> findByTradeOrderByCreatedAtDesc(String trade);
    List<User> findBySquadOrderByCreatedAtDesc(String squad);
    List<User> findByRoleOrderByCreatedAtDesc(String role);
    List<User> findByTradeAndSquadOrderByCreatedAtDesc(String trade, String squad);
    List<User> findByTradeAndRoleOrderByCreatedAtDesc(String trade, String role);
    List<User> findBySquadAndRoleOrderByCreatedAtDesc(String squad, String role);
    List<User> findByTradeAndSquadAndRoleOrderByCreatedAtDesc(String trade, String squad, String role);

}
