package com.tonytor.activityservice.controllers;

import com.tonytor.activityservice.controllers.dto.TaskDTO;
import com.tonytor.activityservice.model.AbstractTask;
import com.tonytor.activityservice.model.Status;
import com.tonytor.activityservice.service.TaskService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
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
        return "Hello world";
    }

    @GetMapping("/{name}")
    public TaskDTO getByName(@PathVariable String name){
        return new TaskDTO(service.getTaskByName(name));
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
    public void createTack(@RequestParam(required = false) String parent, @RequestBody TaskDTO task){

        AbstractTask t = createTackFromDTO(task);

        if(parent==null){
            service.addTask(t);
        } else {
            service.addTask(t, service.getTaskByName(parent));
        }
    }

    @PutMapping("")
    public void updateTask(@RequestBody TaskDTO task){
        AbstractTask t = createTackFromDTO(task);
        service.updateTask(t);
    }

    private AbstractTask createTackFromDTO(TaskDTO task) {
        AbstractTask t = new AbstractTask();
        t.setName(task.getName());
        t.setDescription(task.getDescription());
        t.setBegin(LocalDateTime.parse(task.getBegin()));
        t.setEnd(LocalDateTime.parse(task.getEnd()));
        t.setPercent(task.getPercent());
        t.setStatus(Status.valueOf(task.getStatus()));
        return t;
    }

    @DeleteMapping("/{name}")
    public void deleteTask(@PathVariable String name){
        service.deleteTask(name);
    }





}
