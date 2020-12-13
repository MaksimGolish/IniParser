package com.example.taskmanager.entity;

import com.example.taskmanager.model.TaskState;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String description;
    @OneToOne
    private Employee employee;
    private TaskState state;
    private Instant opened = Instant.now();
    private Instant activated;
    private Instant resolved;

    @Builder
    public Task(String name, String description, Employee employee, TaskState state) {
        this.name = name;
        this.description = description;
        this.employee = employee;
        this.state = state;
    }

    @JsonCreator
    public Task(String name, String description, TaskState state) {
        this.name = name;
        this.description = description;
        this.state = state;
    }

    public void activate() {
        state = TaskState.ACTIVE;
        activated = Instant.now();
    }

    public void resolve() {
        state = TaskState.RESOLVED;
        activated = Instant.now();
    }
}
