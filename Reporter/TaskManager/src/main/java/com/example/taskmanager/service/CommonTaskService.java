package com.example.taskmanager.service;

import com.example.taskdriver.model.TaskDto;
import com.example.taskdriver.service.TaskService;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.exception.EmployeeNotFoundException;
import com.example.taskmanager.exception.TaskNotFoundException;
import com.example.taskmanager.repository.EmployeeRepository;
import com.example.taskmanager.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommonTaskService implements TaskService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(CommonTaskService::taskEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getById(UUID id) {
        return taskEntityToDto(
                taskRepository.
                        findById(id)
                        .orElseThrow(() -> new TaskNotFoundException(id))
        );
    }

    @Override
    public TaskDto addTask(TaskDto taskDto) {
        Task task = taskDtoToEntity(taskDto);
        return taskEntityToDto(taskRepository.save(task));
    }

    @Override
    public TaskDto activate(UUID id) {
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.activate();
        delete(id);
        return taskEntityToDto(taskRepository.save(task));
    }

    @Override
    public TaskDto resolve(UUID id) {
        Task task = taskRepository
                .findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        task.resolve();
        delete(id);
        return taskEntityToDto(taskRepository.save(task));
    }

    @Override
    public TaskDto delete(UUID id) {
        TaskDto task = getById(id);
        taskRepository.deleteById(id);
        return task;
    }

    public static TaskDto taskEntityToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .description(task.getDescription())
                .employee(task.getEmployee())
                .assigner(task.getId())
                .state(task.getState())
                .opened(task.getOpened())
                .activated(task.getActivated())
                .resolved(task.getResolved())
                .build();
    }

    private Task taskDtoToEntity(TaskDto taskDto) {
        return Task.builder()
                .name(taskDto.getName())
                .description(taskDto.getDescription())
                .employee(
                        employeeRepository
                                .findById(taskDto.getEmployee())
                                .orElseThrow(() -> new EmployeeNotFoundException(taskDto.getEmployee()))
                )
                .assigner(
                        employeeRepository
                                .findById(taskDto.getAssigner())
                                .orElseThrow(() -> new EmployeeNotFoundException(taskDto.getAssigner()))
                )
                .state(taskDto.getState())
                .build();
    }
}
