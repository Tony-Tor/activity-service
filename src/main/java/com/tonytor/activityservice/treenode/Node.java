package com.tonytor.activityservice.treenode;

import java.util.*;
import java.util.function.Consumer;

public class Node {

    private String name;
    private NodeHolder holder;
    private Node parent;
    private Nodeable object;
    private Map<String, Node> children;

    public Node(NodeHolder holder, String name) {
        this.holder = holder;
        this.name = name;
        children = new HashMap<>();
        holder.nodes.put(name, this);
        holder.roots.put(name, this);
    }

    public List<Node> pass(Consumer<Node> consumer){
        Node node = this;
        List<Node> passed = new ArrayList<>();
        Deque<Node> stack = new LinkedList<>();
        //List<Node> leaves = new ArrayList<>();
        stack.push(node);
        while(stack.size()!=0){
            node = stack.peek();
            if(!passed.contains(node)){
                consumer.accept(node);//if(node.children.size()==0)leaves.add(node); // место для выполнения метода
                passed.add(node);
            }
            boolean hasChildren = false;
            for(Node n:node.children.values()){
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

    public List<Node> getLeaves(){
        List<Node> leaves = new ArrayList<>();

        pass(node -> {
            if(node.children.size()==0)leaves.add(node);
        });

        return leaves;
    }

    public void upPass(Consumer<Node> consumer){
        Node current = this;
        while(current != null){
            consumer.accept(current);
            current = current.parent;
        }
    }

    public String getName() {
        return name;
    }

    public void addChild(Node node){
        children.put(node.getName(), node);
        node.parent = this;
        holder.roots.remove(node.name);
    }

    public void removeChild(String name){
        Node node = children.get(name);
        node.parent = null;
        node.holder.roots.put(name, node);
        children.remove(name);
    }

    public void removeChild(Node node){
        removeChild(node.getName());
    }

    public void cutBranch(){
        if (this.parent == null) return;
        this.parent.removeChild(this);
    }

    public NodeHolder getHolder() {
        return holder;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        if(parent == null) {
            cutBranch();
            return;
        }
        parent.addChild(this);
    }

    public Nodeable getObject() {
        return object;
    }

    public void setObject(Nodeable object) {
        this.object = object;
    }

    protected List<Node> getChildrenNotSafe() {
        return children.values().stream().toList();
    }

    public List<Node> getChildren(){
        List<Node> nodes = new ArrayList<>();
        for(Node node:children.values()){
            nodes.add(node);
        }
        return nodes;
    }
}
