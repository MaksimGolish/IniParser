package com.example.taskmanager.controller;

import com.example.taskmanager.entity.Task;
import com.example.taskmanager.model.TaskDto;
import com.example.taskmanager.service.TaskService;
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
    public List<Task> getTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable UUID id) {
        return taskService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Task addTask(@RequestBody TaskDto task) {
        return taskService.addTask(task);
    }

    @PutMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Task activateTask(@PathVariable UUID id) {
        return taskService.activate(id);
    }

    @PutMapping("/{id}/resolve")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Task resolveTask(@PathVariable UUID id) {
        return taskService.resolve(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Task deleteTask(@PathVariable UUID id) {
        return taskService.delete(id);
    }
}
