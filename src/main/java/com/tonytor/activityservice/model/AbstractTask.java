package com.tonytor.activityservice.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public abstract class AbstractTask {
    private Long id; // TODO: UUID
    private String name;
    private String description;
    private LocalDateTime begin;
    private Duration duration;
    private double percent;
    private Status status;
}
