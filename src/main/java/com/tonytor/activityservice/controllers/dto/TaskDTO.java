package com.tonytor.activityservice.controllers.dto;

import com.tonytor.activityservice.model.AbstractTask;
import com.tonytor.activityservice.model.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDTO {

    private String id;
    private String name;
    private String description;
    private String begin;
    private String end;
    private double percent;
    private String status;

    public TaskDTO(AbstractTask task){
        id = task.getId().toString();
        name = task.getName();
        description = task.getDescription();
        begin = task.getBegin().toString();
        end = task.getEnd().toString();
        percent = task.getPercent();
        status = task.getStatus().toString();
    }

    public TaskDTO() {

    }
}
