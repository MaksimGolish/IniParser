package com.example.taskdriver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class SprintDto {
    private UUID id;
    private String name;
    private List<TaskDto> tasks;

    @JsonCreator
    public SprintDto(String name) {
        this.name = name;
        tasks = new ArrayList<>();
    }
}
