package com.tonytor.activityservice.repositories;

import com.tonytor.activityservice.model.Status;
import com.tonytor.activityservice.model.Task;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends Neo4jRepository<Task, UUID> {
    Task findOneById(UUID id);

    @Query("MATCH (m:Task)-[r]->(n:Task) WHERE NOT (n)-->() return distinct n,r, collect(m)")
    List<Task> findLeaves();

    @Query("MATCH (n:Task)-[r]->(m:Task) WHERE NOT ()-->(n) return distinct n,r,collect(m)")
    List<Task> findRoots();

    @Query("MATCH (b:Task) WHERE b.id = $id " +
            "SET b.name = $name " +
            "SET b.description = $description " +
            "SET b.begin = $begin " +
            "SET b.end = $end " +
            "SET b.percent = $percent " +
            "SET b.status = $status " +
            "RETURN b")
    Task update(@Param("id") UUID id,
                @Param("name") String name,
                @Param("description") String description,
                @Param("begin") LocalDateTime begin,
                @Param("end") LocalDateTime end,
                @Param("percent") double percent,
                @Param("status") Status status);
}
