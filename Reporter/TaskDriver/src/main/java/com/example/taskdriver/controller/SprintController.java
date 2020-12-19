package com.example.taskdriver.controller;

import com.example.taskdriver.model.SprintDto;
import com.example.taskdriver.model.TaskDto;
import com.example.taskdriver.service.SprintService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/sprint")
@RequiredArgsConstructor
public class SprintController {
    private final SprintService sprintService;

    @GetMapping
    public List<SprintDto> getAllSprints() {
        return sprintService.getAllSprints();
    }

    @GetMapping("/{id}")
    public SprintDto getById(@PathVariable UUID id) {
        return sprintService.findById(id);
    }

    @GetMapping("/{id}/tasks")
    public List<TaskDto> getTasks(@PathVariable UUID id) {
        return sprintService.getTasks(id);
    }

    @GetMapping(path = "/{id}/tasks", params = {"startDate", "endDate"})
    public List<TaskDto> getTasks(@PathVariable UUID id,
                               @RequestParam String startDate,
                               @RequestParam String endDate) {
        return sprintService.getTasksByPeriod(id, startDate, endDate);
    }

    @PostMapping
    public SprintDto createNewSprint(@RequestBody SprintDto sprint) {
        return sprintService.createNewSprint(sprint);
    }

    @PutMapping("/{sprintId}/tasks")
    public SprintDto addTask(@PathVariable UUID sprintId,
                          @RequestParam UUID taskId) {
        return sprintService.addNewTask(sprintId, taskId);
    }
}
