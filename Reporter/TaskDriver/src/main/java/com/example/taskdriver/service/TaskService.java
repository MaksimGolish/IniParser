package com.example.taskdriver.service;

import com.example.taskdriver.entity.Task;
import com.example.taskdriver.model.TaskDto;

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
