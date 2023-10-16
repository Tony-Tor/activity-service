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
    public TaskDTO get(@PathVariable UUID uuid){
        return new TaskDTO(service.get(uuid));
    }

    @GetMapping("/all/{uuid}")
    public Set<TaskDTO> getAll(@PathVariable(required = false) UUID uuid){
        return service.getAll(uuid).stream().map(f->new TaskDTO(f)).collect(Collectors.toSet());
    }

    @GetMapping("/roots/{uuid}")
    public Set<TaskDTO> getRoots(@PathVariable(required = false) UUID uuid){
        return service.getRoots(uuid).stream().map(f->new TaskDTO(f)).collect(Collectors.toSet());
    }

    @GetMapping("/leaves/{uuid}")
    public Set<TaskDTO> getLeaves(@PathVariable(required = false) UUID uuid){
        return service.getLeaves(uuid).stream().map(f->new TaskDTO(f)).collect(Collectors.toSet());
    }

    @PostMapping("")
    public TaskDTO create(@RequestParam(required = false) UUID parent, @RequestBody TaskDTO task){
        Task t = task.createTackFromDTO();
        return new TaskDTO(service.create(t, parent));
    }

    @PutMapping("/{uuid}")
    public Set<TaskDTO> update(@RequestBody TaskDTO task, @PathVariable UUID uuid){
        Task t = task.createTackFromDTO();
        return service.update(t, uuid).stream().map(f->new TaskDTO(f)).collect(Collectors.toSet());
    }

    @DeleteMapping("/{uuid}")
    public TaskDTO delete(@PathVariable UUID uuid){
        return new TaskDTO(service.delete(uuid));
    }

    /*if(parent==null){
            service.addTask(t);
        } else {
            service.addTask(t, parent);
        }

        return t.getId();*/







}
