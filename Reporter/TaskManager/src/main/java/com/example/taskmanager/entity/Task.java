package com.example.taskmanager.entity;

import com.example.taskdriver.model.TaskState;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<TaskUpdateLog> logs = new ArrayList<>();

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
        logs.add(new TaskUpdateLog("Task activated"));
    }

    public void resolve() {
        state = TaskState.RESOLVED;
        activated = Instant.now();
        logs.add(new TaskUpdateLog("Task resolved"));
    }

    public void setName(String name) {
        this.name = name;
        logs.add(new TaskUpdateLog("Task name changed to " + name));
    }

    public void setDescription(String description) {
        this.description = description;
        logs.add(new TaskUpdateLog("Task description changed to " + description));
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
        logs.add(new TaskUpdateLog("Task employee changed to " + employee));
    }

    public void setAssigner(Employee assigner) {
        this.assigner = assigner;
        logs.add(new TaskUpdateLog("Task assigner changed to " + assigner));
    }
}
