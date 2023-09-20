package com.tonytor.activityservice.model;

public enum Status {
    ACTIVE,  //Задачи в потоке
    INACTIVE,
    DELEGATED,

    COMPLETED,  //Задачи завершены
    PARTIALLY_COMPLETED,
    CANCELLED,
    FAILED
}
