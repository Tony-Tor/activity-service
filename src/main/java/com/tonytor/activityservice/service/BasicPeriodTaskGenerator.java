package com.tonytor.activityservice.service;

import com.tonytor.activityservice.model.AbstractTask;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
public class BasicPeriodTaskGenerator implements Generator{

    private AbstractTask parent;
    private AbstractTask template;
    private Duration repeat;
    private LocalDateTime beginGeneration;
    private LocalDateTime endGeneration;


    @Override
    public AbstractTask generate() {
        return null;
    }
}
