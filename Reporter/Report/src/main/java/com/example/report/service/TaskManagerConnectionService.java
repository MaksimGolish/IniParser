package com.example.report.service;

import com.example.report.entity.ReportTask;

import java.util.List;
import java.util.UUID;

public interface TaskManagerConnectionService {
    List<ReportTask> getAllTasks(UUID id);

    List<ReportTask> getAllTasksByDate(UUID id, String startDate, String endDate);
}
