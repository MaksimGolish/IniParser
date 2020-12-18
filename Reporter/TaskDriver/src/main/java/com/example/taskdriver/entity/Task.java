package com.example.taskdriver.entity;

import com.example.taskdriver.model.TaskState;
import com.fasterxml.jackson.annotation.JsonGetter;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Task { // TODO: Create DTO and move to manager implementation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    private String description;
    @ManyToOne
    private Employee employee;
    @ManyToOne
    private Employee assigner;
    private TaskState state;
    private Instant opened = Instant.now();
    private Instant activated;
    private Instant resolved;

    @Builder
    public Task(String name, String description, Employee employee, Employee assigner, TaskState state) {
        this.name = name;
        this.description = description;
        this.employee = employee;
        this.assigner = assigner;
        this.state = state;
    }

    @JsonGetter
    public UUID getEmployee() {
        return employee.getId();
    }

    @JsonGetter
    public UUID getAssigner() {
        return assigner.getId();
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
