package com.tonytor.activityservice.model;

import jakarta.persistence.Id;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Task extends AbstractIdEntity{
    private String name;
    private String description;
    private LocalDateTime begin;
    private LocalDateTime end;
    private double percent;
    private Status status;
}
