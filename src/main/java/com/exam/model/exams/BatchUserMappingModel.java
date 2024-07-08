package com.exam.model.exams;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BatchUserMappingModel {
    private Long batchId;
    private Long userId;
    private String status;
    private Date startedAt;
    private Date completedAt;
    private Date createdAt;
    private Date updatedAt;



}
