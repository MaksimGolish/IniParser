package com.example.taskmanager.service;

import com.example.taskmanager.entity.Employee;
import com.example.taskmanager.entity.Task;
import com.example.taskmanager.exception.EmployeeNotFoundException;
import com.example.taskmanager.repository.EmployeeRepository;
import com.example.taskmanager.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CommonEmployeeService implements EmployeeService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getEmployee(UUID id) {
        return employeeRepository
                .findById(id)
                .orElseThrow(
                        () -> new EmployeeNotFoundException(id)
                );
    }

    @Override
    public Collection<Task> getAllEmployeeTasks(UUID id) {
        return taskRepository.findAllByEmployee(getEmployee(id));
    }

    @Override
    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public Employee setHead(UUID employeeId, UUID headId) {
        Employee employee = getEmployee(employeeId);
        employee.setHead(getEmployee(headId));
        return employeeRepository.save(employee);
    }
}
