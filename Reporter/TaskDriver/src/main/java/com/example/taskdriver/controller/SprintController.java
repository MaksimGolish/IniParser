package com.example.taskdriver.controller;

import com.example.taskdriver.entity.Sprint;
import com.example.taskdriver.entity.Task;
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
    public List<Sprint> getAllSprints() {
        return sprintService.getAllSprints();
    }

    @GetMapping("/{id}")
    public Sprint getById(@PathVariable UUID id) {
        return sprintService.findById(id);
    }

    @GetMapping("/{id}/tasks")
    public List<Task> getTasks(@PathVariable UUID id) {
        return sprintService.getTasks(id);
    }

    @GetMapping(path = "/{id}/tasks", params = {"startDate", "endDate"})
    public List<Task> getTasks(@PathVariable UUID id,
                               @RequestParam String startDate,
                               @RequestParam String endDate) {
        return sprintService.getTasksByPeriod(id, startDate, endDate);
    }

    @PostMapping
    public Sprint createNewSprint(@RequestBody Sprint sprint) {
        return sprintService.createNewSprint(sprint);
    }

    @PutMapping("/{sprintId}/tasks")
    public Sprint addTask(@PathVariable UUID sprintId,
                          @RequestParam UUID taskId) {
        return sprintService.addNewTask(sprintId, taskId);
    }
}
