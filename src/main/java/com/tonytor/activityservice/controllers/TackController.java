package com.tonytor.activityservice.controllers;

import com.tonytor.activityservice.controllers.dto.TaskDTO;
import com.tonytor.activityservice.model.Task;
import com.tonytor.activityservice.model.Status;
import com.tonytor.activityservice.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/task")
public class TackController {

    private TaskService service;

    public TackController(TaskService service) {
        this.service = service;
    }

    @GetMapping("/test")
    public String test(){
        return "It's work";
    }

    @GetMapping("/{uuid}")
    public TaskDTO getByName(@PathVariable UUID uuid){
        return new TaskDTO(service.getTaskUUID(uuid));
    }

    @GetMapping("/all")
    public List<TaskDTO> getAll(){
        return service.getAll().stream().map(f->new TaskDTO(f)).collect(Collectors.toList());
    }

    @GetMapping("/roots")
    public List<TaskDTO> getRoots(){
        return service.getMainTask().stream().map(f->new TaskDTO(f)).collect(Collectors.toList());
    }

    @GetMapping("/leaves")
    public Set<TaskDTO> getLeaves(){
        return service.getLeavesTask().stream().map(f->new TaskDTO(f)).collect(Collectors.toSet());
    }

    @PostMapping("")
    public UUID createTack(@RequestParam(required = false) UUID parent, @RequestBody TaskDTO task){

        Task t = createTackFromDTO(task);

        if(parent==null){
            service.addTask(t);
        } else {
            service.addTask(t, parent);
        }

        return t.getId();
    }

    @PutMapping("")
    public void updateTask(@RequestBody TaskDTO task){
        Task t = createTackFromDTO(task);
        service.updateTask(t);
    }

    private Task createTackFromDTO(TaskDTO task) {
        Task t = new Task();
        t.setName(task.getName());
        t.setDescription(task.getDescription());
        t.setBegin(LocalDateTime.parse(task.getBegin()));
        t.setEnd(LocalDateTime.parse(task.getEnd()));
        t.setPercent(task.getPercent());
        t.setStatus(Status.valueOf(task.getStatus()));
        return t;
    }

    @DeleteMapping("/{uuid}")
    public void deleteTask(@PathVariable UUID uuid){
        service.deleteTask(uuid);
    }





}
