package com.example.taskmanager.service;

import com.example.taskdriver.model.TaskDto;
import com.example.taskmanager.entity.Employee;
import com.example.taskdriver.model.EmployeeDto;
import com.example.taskdriver.service.EmployeeService;
import com.example.taskmanager.exception.EmployeeNotFoundException;
import com.example.taskmanager.repository.EmployeeRepository;
import com.example.taskmanager.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommonEmployeeService implements EmployeeService {
    private final TaskRepository taskRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDto> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(CommonEmployeeService::employeeEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto getEmployee(UUID id) {
        return employeeEntityToDto(
                employeeRepository
                        .findById(id)
                        .orElseThrow(() -> new EmployeeNotFoundException(id))
        );
    }

    @Override
    public Collection<TaskDto> getAllEmployeeTasks(UUID id) {
        return taskRepository
                .findAllByEmployee(getEmployee(id))
                .stream()
                .map(CommonTaskService::taskEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeDto addEmployee(EmployeeDto employee) {

        return employeeEntityToDto(
                employeeRepository.save(new Employee(employee.getName()))
        );
    }

    @Override
    public EmployeeDto setHead(UUID employeeId, UUID headId) {
        Employee employee = employeeRepository
                .findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employee.setHeadId(headId);
        return employeeEntityToDto(employeeRepository.save(employee));
    }

    @Override
    public EmployeeDto getLeads() {
        return employeeEntityToDto(employeeRepository.findAllByHeadIdIsNull());
    }

    public static EmployeeDto employeeEntityToDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .headId(employee.getHeadId())
                .subordinateEmployees(
                        employee.getSubordinateEmployees()
                                .stream()
                                .map(CommonEmployeeService::employeeEntityToDto)
                                .collect(Collectors.toSet())
                )
                .build();
    }
}
