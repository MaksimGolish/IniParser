package com.example.taskdriver.service;

import com.example.taskdriver.model.SprintDto;
import com.example.taskdriver.model.TaskDto;

import java.util.List;
import java.util.UUID;

public interface SprintService {
    List<SprintDto> getAllSprints();

    SprintDto findById(UUID id);

    List<TaskDto> getTasks(UUID id);

    List<TaskDto> getTasksByPeriod(UUID id, String start, String end);

    SprintDto createNewSprint(SprintDto sprint);

    SprintDto addNewTask(UUID sprintId, UUID taskId);
}
