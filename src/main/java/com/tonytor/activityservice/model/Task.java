package com.tonytor.activityservice.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Node("Task")
@Data
public class Task extends AbstractIdEntity{
    private String name;
    private String description;
    private LocalDateTime begin;
    private LocalDateTime end;
    private double percent;
    private Status status;

    private Task parent;
    @Relationship(type = "PARENT_OF", direction = Relationship.Direction.INCOMING)
    private List<Task> children;
}
