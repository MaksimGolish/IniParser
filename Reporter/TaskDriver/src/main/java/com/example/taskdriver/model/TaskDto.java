package com.example.taskdriver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class TaskDto {
    private UUID id;
    private String name;
    private String description;
    private UUID assigner;
    private UUID employee;
    private TaskState state;
    private Instant opened;
    private Instant activated;
    private Instant resolved;
    @JsonIgnore
    private List<TaskLogDto> logs;
    @JsonCreator
    public TaskDto(String name, String description, UUID employee, UUID assigner) {
        this.name = name;
        this.description = description;
        this.employee = employee;
        this.assigner = assigner;
        this.state = TaskState.OPEN;
    }
}
