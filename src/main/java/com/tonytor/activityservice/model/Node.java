package com.tonytor.activityservice.model;

import lombok.Data;

import java.util.*;

@Data
public class Node {
    private NodeHolder holder;
    private Node parent;
    private Object object;
    private List<Node> children;
}
