package com.example.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Employee { // TODO: Create DTO and move to manager implementation
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private UUID headId;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Employee> subordinateEmployees;

    @JsonCreator
    public Employee(String name) {
        this.name = name;
        subordinateEmployees = new HashSet<>();
    }

    public void addSubordinate(Employee employee) {
        subordinateEmployees.add(employee);
    }
}
