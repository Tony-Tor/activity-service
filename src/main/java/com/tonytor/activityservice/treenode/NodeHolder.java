package com.tonytor.activityservice.treenode;

import java.util.*;
import java.util.function.Consumer;

public class NodeHolder {
    protected Map<String, Node> nodes;
    protected Map<String, Node> roots;

    public NodeHolder() {
        nodes = new HashMap<>();
        roots = new HashMap<>();
    }

    public void removeNodeTree(Node node){
        List<Node> ns = new ArrayList<>();

        node.setParent(null);
        node.pass(n -> ns.add(n));
        
        for(Node n: ns){
            nodes.remove(n.getName());
            roots.remove(n.getName());
        }

    }

    public List<Node> getNodes() {
        List<Node> nodes = new ArrayList<>();
        for(Node node:this.nodes.values()){
            nodes.add(node);
        }
        return nodes;
    }

    public List<Node> getRoots(){
        List<Node> nodes = new ArrayList<>();
        for(Node node:roots.values()){
            nodes.add(node);
        }
        return nodes;
    }

    public void pass(Consumer<Node> consumer){
        List<Node> passed = new ArrayList<>();
        for(Node node: nodes.values()){
            if(!passed.contains(node)){
                passGraph(node, passed, consumer);
            }
        }
    }

    public List<Node> getLeaves(){
        List<Node> leaves = new ArrayList<>();
        pass(node -> {
            if(node.getChildrenNotSafe().size()==0)leaves.add(node);
        });
        return leaves;
    }

    private void passGraph(Node node, List<Node> passed, Consumer<Node> consumer){
        passed.addAll(node.pass(consumer));
    }

}

/*Deque<Node> stack = new LinkedList<>();
        stack.push(node);
        while(stack.size()!=0){
            node = stack.peek();
            if(!passed.contains(node)){
                consumer.accept(node);
                passed.add(node);
            }
            boolean hasNeighbors = false;
            for(int i = 0; i < node.getChildrenNotSafe().size(); i++){
                Node n = node.getChildrenNotSafe().get(i);
                if(!passed.contains(n)){
                    stack.push(n);
                    hasNeighbors = true;
                    break;
                }
            }
            if(!hasNeighbors)stack.pop();
        }*/
