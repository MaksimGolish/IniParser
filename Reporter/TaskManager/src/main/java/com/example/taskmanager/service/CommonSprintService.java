package com.example.taskmanager.service;

import com.example.taskdriver.entity.Sprint;
import com.example.taskdriver.entity.Task;
import com.example.taskdriver.service.SprintService;
import com.example.taskdriver.service.TaskService;
import com.example.taskmanager.exception.DateFormatException;
import com.example.taskmanager.exception.SprintNotFoundException;
import com.example.taskmanager.repository.SprintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonSprintService implements SprintService {
    private final SprintRepository sprintRepository;
    private final TaskService taskService;

    public List<Sprint> getAllSprints() {
        return sprintRepository.findAll();
    }

    public Sprint findById(UUID id) {
        return sprintRepository
                .findById(id)
                .orElseThrow(() -> new SprintNotFoundException(id));
    }

    public Sprint createNewSprint(Sprint sprint) {
        return sprintRepository.save(sprint);
    }

    public Sprint addNewTask(UUID sprintId, UUID taskId) {
        Sprint sprint = findById(sprintId);
        sprint.addTask(taskService.getById(taskId));
        return sprintRepository.save(sprint);
    }

    public List<Task> getTasks(UUID id) {
        return sprintRepository
                .findById(id)
                .orElseThrow(() -> new SprintNotFoundException(id))
                .getTasks();
    }

    public List<Task> getTasksByPeriod(UUID id, String startDate, String endDate) {
        try {
            Instant start = LocalDate.parse(startDate)
                    .atStartOfDay((ZoneId.of("UTC")))
                    .toInstant();
            Instant end = LocalDate.parse(endDate)
                    .atStartOfDay((ZoneId.of("UTC")))
                    .toInstant();
            return sprintRepository
                    .findById(id)
                    .orElseThrow(() -> new SprintNotFoundException(id))
                    .getTasks()
                    .stream()
                    .filter(task -> matchesPeriod(task, start, end))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new DateFormatException();
        }
    }

    private boolean matchesPeriod(Task task, Instant start, Instant end) {
        boolean matches = true;
        if (task.getOpened() != null)
            matches = task.getOpened().isAfter(start) && task.getOpened().isBefore(end);
        if (task.getActivated() != null)
            matches = task.getOpened().isAfter(start) && task.getOpened().isBefore(end);
        if (task.getResolved() != null)
            matches = task.getResolved().isAfter(start) && task.getResolved().isBefore(end);
        return matches;
    }
}
