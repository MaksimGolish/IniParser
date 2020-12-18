package com.example.taskdriver.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Employee { // TODO: Create DTO and move to manager implementation
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    @Column
    private String headId;
    @OneToMany(cascade = CascadeType.ALL)
    private Set<Employee> subordinateEmployees;

//    public UUID getHeadId() {
//        return UUID.fromString(headId);
//    }
//
//    public void setHeadId(UUID headId) {
//        this.headId = headId.toString();
//    }

    @JsonCreator
    public Employee(String name) {
        this.name = name;
        subordinateEmployees = new HashSet<>();
    }

    public void addSubordinate(Employee employee) {
        subordinateEmployees.add(employee);
    }
}
