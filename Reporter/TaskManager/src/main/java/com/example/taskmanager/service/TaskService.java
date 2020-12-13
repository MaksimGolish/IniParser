package com.example.taskmanager.service;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.model.TaskDto;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<Task> getAllTasks();

    Task getById(UUID id);

    Task addTask(TaskDto taskDto);

    Task activate(UUID id);

    Task resolve(UUID id);

    Task delete(UUID id);
}
