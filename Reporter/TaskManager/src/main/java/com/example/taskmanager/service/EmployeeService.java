package com.example.taskmanager.service;


import com.example.taskmanager.entity.Employee;
import com.example.taskmanager.entity.Task;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    List<Employee> getAllEmployees();

    Employee getEmployee(UUID id);

    Collection<Task> getAllEmployeeTasks(UUID id);

    Employee addEmployee(Employee employee);

    Employee setHead(UUID employeeId, UUID headId);
}
