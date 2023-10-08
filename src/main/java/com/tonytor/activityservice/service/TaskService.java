package com.tonytor.activityservice.service;

import com.tonytor.activityservice.model.Task;
import com.tonytor.activityservice.model.NodeHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class TaskService {

    public void pass(List<Task> nodes, Consumer<Task> consumer){
        List<Task> passed = new ArrayList<>();
        for(Task node: nodes){
            if(!passed.contains(node)){
                passGraph(node, passed, consumer);
            }
        }
    }

    private void passGraph(Task node, List<Task> passed, Consumer<Task> consumer){
        passed.addAll(pass(node, consumer));
    }

    public List<Task> pass(Task node, Consumer<Task> consumer){
        List<Task> passed = new ArrayList<>();
        Deque<Task> stack = new LinkedList<>();

        stack.push(node);
        while(stack.size()!=0){
            node = stack.peek();
            if(!passed.contains(node)){
                consumer.accept(node);// место для выполнения метода
                passed.add(node);
            }
            boolean hasChildren = false;
            for(Task n:node.getChildren()){
                if(!passed.contains(n)){
                    stack.push(n);
                    hasChildren = true;
                    break;
                }
            }
            if(!hasChildren)stack.pop();
        }
        return passed;
    }

    public List<Task> getLeaves(Task node){
        List<Task> leaves = new ArrayList<>();

        pass(node, n -> {
            if(n.getChildren().size()==0)leaves.add(n);
        });

        return leaves;
    }

    public Task getRoot(Task node){
        Task current = node;
        while(true){
            if(current.getParent()==null) return current;
            current = current.getParent();
        }
    }

    public void upPass(Task node, Consumer<Task> consumer){
        Task current = node;
        while(current != null){
            consumer.accept(current);
            current = current.getParent();
        }
    }

    public void addChild(Task node, Task child){
        node.getChildren().add(child);
        child.setParent(child);
    }

    public void removeChild(Task node, Task child){
        child.setParent(null);
        node.getChildren().remove(child);
    }

    public void cutBranch(Task node){
        if (node.getParent() == null) return;
        removeChild(node.getParent(), node);
    }

    public void setParent(Task node, Task parent) {
        if(parent == null) {
            cutBranch(node);
            return;
        }
        addChild(parent, node);
    }

    public void addTask(NodeHolder holder, Task task){
        task.setHolder(holder);
    }

    public void addTask(NodeHolder holder, Task task, UUID uuid){
        task.setHolder(holder);

        Task par = getNodeUUID(holder, uuid);
        addChild(par,task);
        updateNode(holder, task);
    }

    public Task getTaskUUID(NodeHolder holder, UUID uuid){
        return holder.getNodes().stream()
                .filter(f-> f.getId().equals(uuid))
                .findFirst().get();
    }

    public Task getNodeUUID(NodeHolder holder, UUID uuid){
        return holder.getNodes().stream()
                .filter(f-> f.getId().equals(uuid))
                .findFirst().get();
    }

    public List<Task> getMainTask(NodeHolder holder){
        return holder.getNodes().stream()
                .collect(Collectors.toList());
    }

    public List<Task> getLeavesTask(NodeHolder holder){
        return holder.getNodes().stream()
                .collect(Collectors.toList());
    }

    public List<Task> getAll(NodeHolder holder){
        return holder.getNodes().stream()
                .collect(Collectors.toList());
    }

    public void deleteTask(NodeHolder holder, UUID uuid){
        Task node = getNodeUUID(holder, uuid);
        holder.getNodes().remove(node);
    }

    public void updateTask(NodeHolder holder, Task task){
        Task node = getNodeUUID(holder, task.getId());
        Task t = node;
        t.setBegin(task.getBegin());
        t.setEnd(task.getEnd());
        t.setPercent(task.getPercent());
        t.setDescription(task.getDescription());
        t.setStatus(task.getStatus());

        updateNode(holder, node);

    }

    public void updateNode(NodeHolder holder, Task node){
        upPass(node, n->{
            Task task = n;
            List<Task> children = n.getChildren().stream().collect(Collectors.toList());

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
