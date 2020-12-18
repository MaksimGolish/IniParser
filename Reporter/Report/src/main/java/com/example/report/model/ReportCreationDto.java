package com.example.report.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.util.UUID;

@Data
public class ReportCreationDto {
    private String name;
    private UUID sprintId;

    @JsonCreator
    public ReportCreationDto(String name, UUID sprintId) {
        this.name = name;
        this.sprintId = sprintId;
    }
}
