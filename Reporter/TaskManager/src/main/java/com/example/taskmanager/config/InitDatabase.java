package com.example.taskmanager.config;

import com.example.taskdriver.entity.Employee;
import com.example.taskdriver.entity.Sprint;
import com.example.taskdriver.entity.Task;
import com.example.taskdriver.model.TaskState;
import com.example.taskmanager.repository.EmployeeRepository;
import com.example.taskmanager.repository.SprintRepository;
import com.example.taskmanager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
@Slf4j
public class InitDatabase implements InitializingBean {
    private final TaskRepository taskRepository;
    private final SprintRepository sprintRepository;
    private final EmployeeRepository employeeRepository;

    @Override
    public void afterPropertiesSet() {
//        Employee firstEmployee = employeeRepository.save(
//                new Employee("employee1")
//        );
//        Employee secondEmployee = employeeRepository.save(
//                new Employee("employee2")
//        );
//        Employee lead = new Employee("lead");
//
//        lead.addSubordinate(firstEmployee);
//        lead.addSubordinate(secondEmployee);
//        employeeRepository.save(lead);
        Employee firstEmployee = new Employee("employee1");
        Employee secondEmployee = new Employee("employee2");
        Employee lead = new Employee("lead");
        lead.addSubordinate(firstEmployee);
        lead.addSubordinate(secondEmployee);
        lead = employeeRepository.save(lead);
        firstEmployee.setHeadId(lead.getId().toString());
        secondEmployee.setHeadId(lead.getId().toString());
        employeeRepository.save(lead);
        Task firstExampleTask = taskRepository.save(
                Task.builder()
                        .name("Task1")
                        .description("First example task")
                        .employee(firstEmployee)
                        .assigner(lead)
                        .state(TaskState.OPEN)
                        .build()
        );
        Task secondExampleTask = taskRepository.save(
                Task.builder()
                        .name("Task1")
                        .description("First example task")
                        .employee(firstEmployee)
                        .assigner(lead)
                        .state(TaskState.OPEN)
                        .build()
        );
        Sprint sprint = sprintRepository.save(new Sprint("Sprint example"));
        sprint.addTask(firstExampleTask);
        sprint.addTask(secondExampleTask);
        sprintRepository.save(sprint);
    }
}
