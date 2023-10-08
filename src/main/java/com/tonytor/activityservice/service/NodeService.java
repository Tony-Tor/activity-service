package com.tonytor.activityservice.service;

import com.tonytor.activityservice.model.Node;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class NodeService {

    public void pass(List<Node> nodes, Consumer<Node> consumer){
        List<Node> passed = new ArrayList<>();
        for(Node node: nodes){
            if(!passed.contains(node)){
                passGraph(node, passed, consumer);
            }
        }
    }

    private void passGraph(Node node, List<Node> passed, Consumer<Node> consumer){
        passed.addAll(pass(node, consumer));
    }

    public List<Node> pass(Node node, Consumer<Node> consumer){
        List<Node> passed = new ArrayList<>();
        Deque<Node> stack = new LinkedList<>();

        stack.push(node);
        while(stack.size()!=0){
            node = stack.peek();
            if(!passed.contains(node)){
                consumer.accept(node);// место для выполнения метода
                passed.add(node);
            }
            boolean hasChildren = false;
            for(Node n:node.getChildren()){
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

    public List<Node> getLeaves(Node node){
        List<Node> leaves = new ArrayList<>();

        pass(node, n -> {
            if(n.getChildren().size()==0)leaves.add(n);
        });

        return leaves;
    }

    public Node getRoot(Node node){
        Node current = node;
        while(true){
            if(current.getParent()==null) return current;
            current = current.getParent();
        }
    }

    public void upPass(Node node, Consumer<Node> consumer){
        Node current = node;
        while(current != null){
            consumer.accept(current);
            current = current.getParent();
        }
    }

    public void addChild(Node node, Node child){
        node.getChildren().add(child);
        child.setParent(child);
    }

    public void removeChild(Node node, Node child){
        child.setParent(null);
        node.getChildren().remove(child);
    }

    public void cutBranch(Node node){
        if (node.getParent() == null) return;
        removeChild(node.getParent(), node);
    }

    public void setParent(Node node, Node parent) {
        if(parent == null) {
            cutBranch(node);
            return;
        }
        addChild(parent, node);
    }

}
