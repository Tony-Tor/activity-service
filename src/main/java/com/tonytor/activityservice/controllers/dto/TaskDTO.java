package com.tonytor.activityservice.controllers.dto;

import com.tonytor.activityservice.model.Status;
import com.tonytor.activityservice.model.Task;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TaskDTO {

    private String id;
    private String name;
    private String description;
    private String begin;
    private String end;
    private double percent;
    private String status;
    private String parent;
    private List<String> children;

    public TaskDTO(Task task){
        if(task.getId() != null)id = task.getId().toString();
        name = task.getName();
        description = task.getDescription();
        begin = task.getBegin().toString();
        end = task.getEnd().toString();
        percent = task.getPercent();
        status = task.getStatus().toString();
        Task parent = task.getParent();
        if(parent!=null){
            this.parent=parent.getId().toString();
        }
        this.children = new ArrayList<>();
        List<Task> children = task.getChildren();
        if(children!=null){
            for(Task ch:children){
                this.children.add(ch.getId().toString());
            }
        }
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
