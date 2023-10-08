package com.tonytor.activityservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;


@MappedSuperclass
@Access(AccessType.FIELD)
@Data
public abstract class AbstractIdEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected UUID id;
}
