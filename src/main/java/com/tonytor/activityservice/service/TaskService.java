package com.tonytor.activityservice.service;

import com.tonytor.activityservice.model.AbstractTask;
import com.tonytor.activityservice.treenode.Node;
import com.tonytor.activityservice.treenode.NodeHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
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

        updateNode(node);
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

        //Node root = node.getRoot();
        updateNode(node);

    }

    public void updateNode(Node node){
        node.upPass(n->{
            AbstractTask task = (AbstractTask) n.getObject();
            List<AbstractTask> children = n.getChildren().stream().map(f->(AbstractTask)f.getObject()).collect(Collectors.toList());

            if(children.size() != 0){
                LocalDateTime minStart = children.stream().min(Comparator.comparing(AbstractTask::getBegin)).get().getBegin();
                LocalDateTime maxEnd = children.stream().max(Comparator.comparing(AbstractTask::getEnd)).get().getEnd();
                double avgPercent = children.stream().mapToDouble(f->f.getPercent()).average().getAsDouble();

                task.setBegin(minStart);
                task.setEnd(maxEnd);
                task.setPercent(avgPercent);
            }
        });
    }


}
