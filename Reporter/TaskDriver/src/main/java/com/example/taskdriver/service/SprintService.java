package com.example.taskdriver.service;

import com.example.taskdriver.entity.Sprint;
import com.example.taskdriver.entity.Task;

import java.util.List;
import java.util.UUID;

public interface SprintService {
    List<Sprint> getAllSprints();

    Sprint findById(UUID id);

    List<Task> getTasks(UUID id);

    List<Task> getTasksByPeriod(UUID id, String start, String end);

    Sprint createNewSprint(Sprint sprint);

    Sprint addNewTask(UUID sprintId, UUID taskId);
}
