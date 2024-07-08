package com.exam.model.exams;

import com.exam.entity.Result;
import com.exam.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResultModel {
    private long quizId;
	private long result_id;
    private int qAttempted;
    private int correctAns;
    private double marksScored;
    private String submitDateTime;
	private long userId;
	private String firstName;
	private String lastName;
	private String armyNo;
	private String armyRank;
	private String unit;
	private String trade;
	private String squad;



	public UserResultModel(Optional<User> user, Result result){


		BeanUtils.copyProperties(user.get(),this);
//		this.userId=user.get().getId();
//		this. userName=user.get().getUsername();
//		this. firstName=user.get().getFirstName();
//		this. lastName=user.get().getLastName();
//		this. armyNo=user.get().getArmyNo();
//		this. armyRank=user.get().getArmyRank();
//		this. unit=user.get().getUnit();
//		this. trade=user.get().getTrade();
		BeanUtils.copyProperties(result,this);

	}
}
