package com.tonytor.activityservice.model;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.*;

@Node("NodeHolder")
@Data
public class NodeHolder {
    @Relationship(type = "CONTAINS", direction = Relationship.Direction.OUTGOING)
    private List<Task> nodes;
}

