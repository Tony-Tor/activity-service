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

    private NodeService service;

    public TaskService(NodeService service) {
        this.service = service;
    }

    public void addTask(NodeHolder holder, Task task){
        Node node = new Node();
        node.setHolder(holder);

        node.setObject(task);
    }

    public void addTask(NodeHolder holder, Task task, UUID uuid){
        Node node = new Node();
        node.setHolder(holder);

        node.setObject(task);
        Node par = getNodeUUID(holder, uuid);
        service.addChild(par,node);
        updateNode(holder, node);
    }

    public Task getTaskUUID(NodeHolder holder, UUID uuid){
        return holder.getNodes().stream()
                .map(f->(Task) f.getObject())
                .filter(f-> f.getId().equals(uuid))
                .findFirst().get();
    }

    public Node getNodeUUID(NodeHolder holder, UUID uuid){
        return holder.getNodes().stream()
                .filter(f-> ((Task)f.getObject()).getId().equals(uuid))
                .findFirst().get();
    }

    public List<Task> getMainTask(NodeHolder holder){
        return holder.getNodes().stream().map(f->(Task) f.getObject()).collect(Collectors.toList());
    }

    public List<Task> getLeavesTask(NodeHolder holder){
        return holder.getNodes().stream().map(f->(Task) f.getObject()).collect(Collectors.toList());
    }

    public List<Task> getAll(NodeHolder holder){
        return holder.getNodes().stream().map(f->(Task) f.getObject()).collect(Collectors.toList());
    }

    public void deleteTask(NodeHolder holder, UUID uuid){
        Node node = getNodeUUID(holder, uuid);
        holder.getNodes().remove(node);
    }

    public void updateTask(NodeHolder holder, Task task){
        Node node = getNodeUUID(holder, task.getId());
        Task t = (Task) node.getObject();
        t.setBegin(task.getBegin());
        t.setEnd(task.getEnd());
        t.setPercent(task.getPercent());
        t.setDescription(task.getDescription());
        t.setStatus(task.getStatus());

        updateNode(holder, node);

    }

    public void updateNode(NodeHolder holder, Node node){
        service.upPass(node, n->{
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
