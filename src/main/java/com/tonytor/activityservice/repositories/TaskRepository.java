package com.tonytor.activityservice.repositories;

import com.tonytor.activityservice.model.Task;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskRepository extends Neo4jRepository<Task, UUID> {
    Task findOneById(UUID id);
}
