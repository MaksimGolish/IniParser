package com.example.taskmanager.service;

import com.example.taskmanager.model.TaskDto;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommonTaskService implements TaskService {
    private final TaskRepository repository;
    private final CommonEmployeeService employeeService;

    @Override
    public List<Task> getAllTasks() {
        return repository.findAll();
    }

    @Override
    public Task getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    @Override
    public Task addTask(TaskDto taskDto) {
        return repository.save(Task.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .employee(employeeService.getEmployee(taskDto.getEmployee()))
                .state(taskDto.getState())
                .build());
    }

    @Override
    public Task activate(UUID id) {
        Task task = getById(id);
        task.activate();
        delete(id);
        return repository.save(task);
    }

    @Override
    public Task resolve(UUID id) {
        Task task = getById(id);
        task.resolve();
        delete(id);
        return repository.save(task);
    }

    @Override
    public Task delete(UUID id) {
        Task task = getById(id);
        repository.deleteById(id);
        return task;
    }
}
