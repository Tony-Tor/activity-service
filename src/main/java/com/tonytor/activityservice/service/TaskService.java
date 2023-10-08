package com.tonytor.activityservice.service;

import com.tonytor.activityservice.model.Task;
import com.tonytor.activityservice.model.Node;
import com.tonytor.activityservice.model.NodeHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TaskService {

    NodeHolder holder = new NodeHolder();

    public void addTask(Task task){
        Node node = new Node(holder);
        node.setObject(task);
    }

    public void addTask(Task task, UUID uuid){
        Node node = new Node(holder);
        node.setObject(task);
        Node par = getNodeUUID(uuid);
        par.addChild(node);

        updateNode(node);
    }

    public Task getTaskUUID(UUID uuid){
        return holder.getNodes().stream()
                .map(f->(Task) f.getObject())
                .filter(f-> f.getId().equals(uuid))
                .findFirst().get();
    }

    public Node getNodeUUID(UUID uuid){
        return holder.getNodes().stream()
                .filter(f-> ((Task)f.getObject()).getId().equals(uuid))
                .findFirst().get();
    }

    public List<Task> getMainTask(){
        return holder.getRoots().stream().map(f->(Task) f.getObject()).collect(Collectors.toList());
    }

    public List<Task> getLeavesTask(){
        return holder.getLeaves().stream().map(f->(Task) f.getObject()).collect(Collectors.toList());
    }

    public List<Task> getAll(){
        return holder.getNodes().stream().map(f->(Task) f.getObject()).collect(Collectors.toList());
    }

    public void deleteTask(UUID uuid){
        Node node = getNodeUUID(uuid);
        holder.removeNodeTree(node);
    }

    public void updateTask(Task task){
        Node node = getNodeUUID(task.getId());
        Task t = (Task) node.getObject();
        t.setBegin(task.getBegin());
        t.setEnd(task.getEnd());
        t.setPercent(task.getPercent());
        t.setDescription(task.getDescription());
        t.setStatus(task.getStatus());

        updateNode(node);

    }

    public void updateNode(Node node){
        node.upPass(n->{
            Task task = (Task) n.getObject();
            List<Task> children = n.getChildren().stream().map(f->(Task)f.getObject()).collect(Collectors.toList());

            if(children.size() != 0){
                LocalDateTime minStart = children.stream().min(Comparator.comparing(Task::getBegin)).get().getBegin();
                LocalDateTime maxEnd = children.stream().max(Comparator.comparing(Task::getEnd)).get().getEnd();
                double avgPercent = children.stream().mapToDouble(f->f.getPercent()).average().getAsDouble();

                task.setBegin(minStart);
                task.setEnd(maxEnd);
                task.setPercent(avgPercent);
            }
        });
    }


}
