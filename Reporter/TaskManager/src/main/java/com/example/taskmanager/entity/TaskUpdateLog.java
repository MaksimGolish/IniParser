package com.example.taskmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String info;
    private Instant time;

    public TaskUpdateLog(String info) {
        this.info = info;
        this.time = Instant.now();
    }
}
