package com.example.taskmanager.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @ManyToOne
    private Employee head;
    @OneToMany
    private Set<Employee> subordinateEmployees;

    @JsonCreator
    public Employee(String name) {
        this.name = name;
        subordinateEmployees = new HashSet<>();
    }
}
