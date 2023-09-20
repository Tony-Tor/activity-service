package com.tonytor.activityservice.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivityTask {
    private double productivity;
    private LocalDateTime begin;
    private LocalDateTime rest;
    private LocalDateTime end;

}
