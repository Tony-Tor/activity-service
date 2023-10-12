package com.tonytor.activityservice.controllers.dto;

import com.tonytor.activityservice.model.Status;
import com.tonytor.activityservice.model.Task;
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

    public TaskDTO(Task task){
        if(task.getId() != null)id = task.getId().toString();
        name = task.getName();
        description = task.getDescription();
        begin = task.getBegin().toString();
        end = task.getEnd().toString();
        percent = task.getPercent();
        status = task.getStatus().toString();
    }

    public TaskDTO() {

    }

    public Task createTackFromDTO() {
        Task t = new Task();
        t.setName(getName());
        t.setDescription(getDescription());
        t.setBegin(LocalDateTime.parse(getBegin()));
        t.setEnd(LocalDateTime.parse(getEnd()));
        t.setPercent(getPercent());
        t.setStatus(Status.valueOf(getStatus()));
        return t;
    }
}
