package com.tonytor.activityservice.model;

import lombok.Data;

import java.util.List;

@Data
public class BasicTask extends AbstractTask{
    private double percent;
    private Status status;
}
