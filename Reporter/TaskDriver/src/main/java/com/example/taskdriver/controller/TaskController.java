package com.example.taskdriver.controller;

import com.example.taskdriver.model.TaskDto;
import com.example.taskdriver.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<TaskDto> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable UUID id) {
        return taskService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto addTask(@RequestBody TaskDto task) {
        return taskService.addTask(task);
    }

    @PutMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto activateTask(@PathVariable UUID id) {
        return taskService.activate(id);
    }

    @PutMapping("/{id}/resolve")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto resolveTask(@PathVariable UUID id) {
        return taskService.resolve(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskDto deleteTask(@PathVariable UUID id) {
        return taskService.delete(id);
    }
}
