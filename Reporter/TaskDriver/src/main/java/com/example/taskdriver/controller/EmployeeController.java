package com.example.taskdriver.controller;

import com.example.taskdriver.model.EmployeeDto;
import com.example.taskdriver.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable UUID id) {
        return employeeService.getEmployee(id);
    }
//
//    @GetMapping("/{id}/tasks")
//    public Collection<TaskDto> getAllEmployeeTasks(@PathVariable UUID id) {
//        return employeeService.getAllEmployeeTasks(id);
//    }

    @GetMapping("/leads")
    public EmployeeDto getLeads() {
        return employeeService.getLeads();
    }

    @PostMapping
    public EmployeeDto addEmployee(@RequestBody EmployeeDto employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{employeeId}/head/{headId}")
    public EmployeeDto setHead(@PathVariable UUID employeeId, @PathVariable UUID headId) {
        return employeeService.setHead(employeeId, headId);
    }
}
