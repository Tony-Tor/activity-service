package com.tonytor.activityservice.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AbstractTask {
    private UUID id; // TODO: UUID
    private String name;
    private String description;
    private LocalDateTime begin;
    private LocalDateTime end;
    private double percent;
    private Status status;
}
