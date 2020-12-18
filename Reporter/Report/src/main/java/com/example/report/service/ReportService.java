package com.example.report.service;

import com.example.report.model.ReportTaskState;
import com.example.report.entity.Report;
import com.example.report.model.ReportCreationDto;
import com.example.report.entity.ReportTask;
import com.example.report.repository.ReportRepository;
import com.example.report.repository.ReportTaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportService {
    private final ManagerConnectionService connectionService;
    private final ReportRepository reportRepository;
    private final ReportTaskRepository taskRepository;

    public Report createNewReport(ReportCreationDto creationDto,
                                  UUID employeeId,
                                  UUID assignerId) {
        List<ReportTask> tasks = connectionService
                .getAllTasks(creationDto.getSprintId())
                .stream()
                .map(taskRepository::save)
                .filter(task -> {
                    if (employeeId != null && !task.getEmployee().equals(employeeId))
                        return false;
                    if (assignerId != null && !task.getAssigner().equals(assignerId))
                        return false;
                    return true;
                })
                .collect(Collectors.toList());
        return reportRepository.save(
                createReportFromTasks(creationDto.getName(), tasks)
        );
    }

    public Report createNewReportByDate(ReportCreationDto creationDto,
                                        UUID employeeId,
                                        UUID assignerId,
                                        String startDate,
                                        String endDate) {
        List<ReportTask> tasks = connectionService
                .getAllTasksByDate(creationDto.getSprintId(), startDate, endDate)
                .stream()
                .map(taskRepository::save)
                .filter(task -> {
                    if (employeeId != null && !task.getEmployee().equals(employeeId))
                        return false;
                    if (assignerId != null && !task.getAssigner().equals(assignerId))
                        return false;
                    return true;
                })
                .collect(Collectors.toList());
        return reportRepository.save(
                createReportFromTasks(creationDto.getName(), tasks)
        );
    }

    private Report createReportFromTasks(String name,
                                         List<ReportTask> tasks) {
        Report report = new Report(name);
        report.setOpened(
                tasks.stream()
                        .filter(task -> task.getState() == ReportTaskState.OPEN)
                        .collect(Collectors.toSet())
        );
        report.setActivated(
                tasks.stream()
                        .filter(task -> task.getState() == ReportTaskState.ACTIVE)
                        .collect(Collectors.toSet())
        );
        report.setResolved(
                tasks.stream()
                        .filter(task -> task.getState() == ReportTaskState.RESOLVED)
                        .collect(Collectors.toSet())
        );
        return report;
    }

    public Report getById(UUID id) {
        return reportRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }
}
