package com.example.taskdriver.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class EmployeeDto {
    private UUID id;
    private String name;
    private UUID headId;

    private Set<EmployeeDto> subordinateEmployees;

    @JsonCreator
    public EmployeeDto(String name) {
        this.name = name;
        subordinateEmployees = new HashSet<>();
    }

    @Builder
    public EmployeeDto(UUID id, String name, UUID headId, Set<EmployeeDto> subordinateEmployees) {
        this.id = id;
        this.name = name;
        this.headId = headId;
        this.subordinateEmployees = subordinateEmployees;
    }

    public void addSubordinate(EmployeeDto employee) {
        subordinateEmployees.add(employee);
    }
}
