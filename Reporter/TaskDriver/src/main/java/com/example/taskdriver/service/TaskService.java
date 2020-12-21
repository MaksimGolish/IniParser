package com.example.taskdriver.service;

import com.example.taskdriver.model.TaskDto;

import java.util.List;
import java.util.UUID;

public interface TaskService {
    List<TaskDto> getAllTasks();

    TaskDto getById(UUID id);

    TaskDto addTask(TaskDto taskDto);

    TaskDto activate(UUID id);

    TaskDto resolve(UUID id);

    TaskDto delete(UUID id);
}
