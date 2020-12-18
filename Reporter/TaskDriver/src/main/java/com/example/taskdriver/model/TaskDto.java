package com.example.taskdriver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.util.UUID;

@Data
public class TaskDto {
    private String name;
    private String description;
    private UUID assigner;
    private UUID employee;
    private TaskState state;

    @JsonCreator
    public TaskDto(String name, String description, UUID employee, UUID assigner) {
        this.name = name;
        this.description = description;
        this.employee = employee;
        this.assigner = assigner;
        this.state = TaskState.OPEN;
    }
}
