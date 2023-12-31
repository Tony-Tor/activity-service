package com.tonytor.activityservice.service;

import com.tonytor.activityservice.model.Task;
import com.tonytor.activityservice.repositories.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class TaskService {

    private TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    //Create
    public Task create(Task task, UUID parent){
        task.setParent(get(parent));
        Task res = repository.save(task);
        if(parent!=null)update(get(parent),parent);
        return res;
    }

    //Read
    public Task get(UUID uuid){
        return repository.findOneById(uuid);
    }

    public List<Task> getAll(UUID uuid){
        return repository.findAll();
    }

    public List<Task> getRoots(UUID uuid){
        return repository.findRoots();
    }

    public List<Task> getLeaves(UUID uuid){
        return repository.findLeaves();
    }

    //Update
    public List<Task> update(Task task, UUID uuid){
        List<Task> nodes = new ArrayList<>();
        Task node = get(uuid);
        node.setName(task.getName());
        node.setBegin(task.getBegin());
        node.setEnd(task.getEnd());
        node.setPercent(task.getPercent());
        node.setDescription(task.getDescription());
        node.setStatus(task.getStatus());

        upPass(node, n->{
            List<Task> children = n.getChildren().stream().collect(Collectors.toList());

            if(children.size() != 0){
                LocalDateTime minStart = children.stream().min(Comparator.comparing(Task::getBegin)).get().getBegin();
                LocalDateTime maxEnd = children.stream().max(Comparator.comparing(Task::getEnd)).get().getEnd();
                double avgPercent = children.stream().mapToDouble(f->f.getPercent()).average().getAsDouble();

                n.setBegin(minStart);
                n.setEnd(maxEnd);
                n.setPercent(avgPercent);
            }
            nodes.add(n);
            repository.update(
                    n.getId(),
                    n.getName(),
                    n.getDescription(),
                    n.getBegin(),
                    n.getEnd(),
                    n.getPercent(),
                    n.getStatus());
        });

        return nodes;
    }

    //Delete
    public Task delete(UUID uuid){
        Task deleted = get(uuid);
        repository.deleteById(uuid);
        return deleted;
    }

    public void upPass(Task node, Consumer<Task> consumer){
        Task current = node;
        while(current != null){
            consumer.accept(current);
            current = current.getParent();
        }
    }
}

/*
    //Методы для обхода графа
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

    public void upPass(Task node, Consumer<Task> consumer){
        Task current = node;
        while(current != null){
            consumer.accept(current);
            current = current.getParent();
        }
    }

    //Методы получения элементов графа
    //Получение корней
    public Task getRoot(Task node){
        Task current = node;
        while(true){
            if(current.getParent()==null) return current;
            current = current.getParent();
        }
    }

    public List<Task> getMainTask(){
        return holder.getNodes().stream()
                .collect(Collectors.toList());
    }

    //Получение листьев
    public List<Task> getLeaves(Task node){
        List<Task> leaves = new ArrayList<>();

        pass(node, n -> {
            if(n.getChildren().size()==0)leaves.add(n);
        });

        return leaves;
    }

    public List<Task> getLeavesTask(){
        return holder.getNodes().stream()
                .collect(Collectors.toList());
    }

    //Получение всех элементов
    public List<Task> getAll(){
        return holder.getNodes().stream()
                .collect(Collectors.toList());
    }

    public Task getTaskUUID(UUID uuid){
        return holder.getNodes().stream()
                .filter(f-> f.getId().equals(uuid))
                .findFirst().get();
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

    public void addTask(Task task){

    }

    public void addTask(Task task, UUID uuid){
        Task par = getNodeUUID(uuid);
        addChild(par,task);
        updateNode(task);
    }

    public Task getNodeUUID(UUID uuid){
        return holder.getNodes().stream()
                .filter(f-> f.getId().equals(uuid))
                .findFirst().get();
    }

    public void deleteTask(UUID uuid){
        Task node = getNodeUUID(uuid);
        holder.getNodes().remove(node);
    }


*/
