package com.example.taskdriver.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Sprint { // TODO: Create DTO and move to manager implementation
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;

    @OneToMany
    private List<Task> tasks;

    public void addTask(Task task) {
        tasks.add(task);
    }

    @JsonCreator
    public Sprint(String name) {
        this.name = name;
        tasks = new ArrayList<>();
    }
}
