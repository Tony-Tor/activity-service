package com.tonytor.activityservice.service;

import com.tonytor.activityservice.model.AbstractTask;
import com.tonytor.activityservice.treenode.Node;
import com.tonytor.activityservice.treenode.NodeHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    NodeHolder holder = new NodeHolder();

    public void addTask(AbstractTask task){
        Node node = new Node(holder,task.getName());
        node.setObject(task);
    }

    public void addTask(AbstractTask task, AbstractTask parent){
        Node node = new Node(holder,task.getName());
        node.setObject(task);
        Node par = holder.getNodeByName(parent.getName());
        par.addChild(node);
    }

    public AbstractTask getTaskByName(String name){
        return (AbstractTask) holder.getNodeByName(name).getObject();
    }

    public List<AbstractTask> getMainTask(){
        return holder.getRoots().stream().map(f->(AbstractTask) f.getObject()).collect(Collectors.toList());
    }

    public List<AbstractTask> getLeavesTask(){
        return holder.getLeaves().stream().map(f->(AbstractTask) f.getObject()).collect(Collectors.toList());
    }

    public List<AbstractTask> getAll(){
        return holder.getNodes().stream().map(f->(AbstractTask) f.getObject()).collect(Collectors.toList());
    }

    public void deleteTask(String name){
        Node par = holder.getNodeByName(name);
        holder.removeNodeTree(par);
    }

    public void updateTask(AbstractTask task){
        Node node = holder.getNodeByName(task.getName());
        AbstractTask t = (AbstractTask) node.getObject();
        t.setBegin(task.getBegin());
        t.setEnd(task.getEnd());
        t.setPercent(task.getPercent());
        t.setDescription(task.getDescription());
        t.setStatus(task.getStatus());

        Node root = node.getRoot();

    }


}
