package com.example.report.service;

import com.example.report.entity.Report;
import com.example.report.model.ReportCreationDto;

import java.util.List;
import java.util.UUID;

public interface ReportService {
    Report createNewReport(ReportCreationDto creationDto,
                           UUID employeeId,
                           UUID assignerId);

    Report createNewReportByDate(ReportCreationDto creationDto,
                                 UUID employeeId,
                                 UUID assignerId,
                                 String startDate,
                                 String endDate);

    Report getById(UUID id);

    List<Report> getAllReports();
}
