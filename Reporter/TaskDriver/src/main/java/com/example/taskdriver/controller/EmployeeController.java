package com.example.taskdriver.controller;

import com.example.taskdriver.entity.Employee;
import com.example.taskdriver.entity.Task;
import com.example.taskdriver.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable UUID id) {
        return employeeService.getEmployee(id);
    }

    @GetMapping("/{id}/tasks")
    public Collection<Task> getAllEmployeeTasks(@PathVariable UUID id) {
        return employeeService.getAllEmployeeTasks(id);
    }

    @GetMapping("/leads")
    public List<Employee> getLeads() {
        return employeeService.getLeads();
    }

    @PostMapping
    public Employee addEmployee(@RequestBody Employee employee) {
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{employeeId}/head/{headId}")
    public Employee setHead(@PathVariable UUID employeeId, @PathVariable UUID headId) {
        return employeeService.setHead(employeeId, headId);
    }
}
