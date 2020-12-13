package com.example.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskDto {
    private String name;
    private String description;
    private UUID employee;
    private TaskState state;

    @JsonCreator
    public TaskDto(String name, String description, UUID employee) {
        this.name = name;
        this.description = description;
        this.employee = employee;
        this.state = TaskState.OPEN;
    }
}
