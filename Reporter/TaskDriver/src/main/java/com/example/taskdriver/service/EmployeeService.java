package com.example.taskdriver.service;

import com.example.taskdriver.model.EmployeeDto;
import com.example.taskdriver.model.TaskDto;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface EmployeeService {
    List<EmployeeDto> getAllEmployees();

    EmployeeDto getEmployee(UUID id);

    Collection<TaskDto> getAllEmployeeTasks(UUID id);

    EmployeeDto addEmployee(EmployeeDto employee);

    EmployeeDto setHead(UUID employeeId, UUID headId);

    EmployeeDto getLeads();
}
