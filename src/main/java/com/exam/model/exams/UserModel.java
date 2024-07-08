package com.exam.model.exams;

import com.exam.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String armyNo;
    private String armyRank;
    private String unit;
    private String trade;
    private boolean enabled = true;
    private String profile;
    private String role;
    private String squad;

    public UserModel(User user){
        BeanUtils.copyProperties(user,this);
        this.setPassword("");
    }
}
