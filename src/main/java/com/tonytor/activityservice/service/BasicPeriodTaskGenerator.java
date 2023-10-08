package com.tonytor.activityservice.service;

import com.tonytor.activityservice.model.Task;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class BasicPeriodTaskGenerator implements Generator{

    private Task parent;
    private Task template;
    private Duration repeat;
    private LocalDateTime beginGeneration;
    private LocalDateTime endGeneration;


    @Override
    public Task generate() {
        return null;
    }
}
