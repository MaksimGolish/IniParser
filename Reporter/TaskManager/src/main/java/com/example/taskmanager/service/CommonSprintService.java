package com.example.taskmanager.service;

import com.example.taskdriver.model.SprintDto;
import com.example.taskdriver.model.TaskDto;
import com.example.taskmanager.entity.Sprint;
import com.example.taskmanager.entity.Task;
import com.example.taskdriver.service.SprintService;
import com.example.taskmanager.exception.DateFormatException;
import com.example.taskmanager.exception.SprintNotFoundException;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.SprintRepository;
import com.example.taskmanager.repository.TaskRepository;
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
    private final TaskRepository taskRepository;

    public List<SprintDto> getAllSprints() {
        return sprintRepository.findAll()
                .stream()
                .map(CommonSprintService::sprintEntityToDto)
                .collect(Collectors.toList());
    }

    public SprintDto findById(UUID id) {
        return sprintEntityToDto(sprintRepository
                .findById(id)
                .orElseThrow(() -> new SprintNotFoundException(id))
        );
    }

    public SprintDto createNewSprint(SprintDto sprint) {
        return sprintEntityToDto(
                sprintRepository.save(
                        sprintDtoToEntity(sprint)
                )
        );
    }

    public SprintDto addNewTask(UUID sprintId, UUID taskId) {
        Sprint sprint = sprintRepository
                .findById(sprintId)
                .orElseThrow(() -> new SprintNotFoundException(sprintId));
        sprint.addTask(
                taskRepository
                        .findById(taskId)
                        .orElseThrow(() -> new TaskNotFoundException(taskId)));
        return sprintEntityToDto(
                sprintRepository.save(sprint)
        );
    }

    public List<TaskDto> getTasks(UUID id) {
        return sprintRepository
                .findById(id)
                .orElseThrow(() -> new SprintNotFoundException(id))
                .getTasks()
                .stream()
                .map(CommonTaskService::taskEntityToDto)
                .collect(Collectors.toList());
    }

    public List<TaskDto> getTasksByPeriod(UUID id, String startDate, String endDate) {
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
                    .map(CommonTaskService::taskEntityToDto)
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

    public static SprintDto sprintEntityToDto(Sprint sprint) {
        return SprintDto.builder()
                .id(sprint.getId())
                .name(sprint.getName())
                .tasks(sprint.getTasks()
                        .stream()
                        .map(CommonTaskService::taskEntityToDto)
                        .collect(Collectors.toList())
                )
                .build();
    }

    public static Sprint sprintDtoToEntity(SprintDto sprintDto) {
        return new Sprint(sprintDto.getName());
    }
}
