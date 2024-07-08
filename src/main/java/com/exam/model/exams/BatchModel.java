package com.exam.model.exams;

import com.exam.entity.Batch;
import com.exam.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BatchModel {

    private long id;
    private String name;
    private LocalDate date;
    private LocalTime time;
    private List<Long> userIds;
    private List<UserModel> userDetails;
    private long quizId;
    private Integer traineeCount;
//	private boolean status;
    private String trade;


}
