package com.example.report.controller;

import com.example.report.entity.Report;
import com.example.report.model.ReportCreationDto;
import com.example.report.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {
    private final ReportService service;

    @PostMapping
    public Report createNewReport(@RequestBody ReportCreationDto reportCreationDto,
                                  @RequestParam(required = false) UUID employeeId,
                                  @RequestParam(required = false) UUID assignerId) {
        return service.createNewReport(
                reportCreationDto,
                employeeId,
                assignerId
        );
    }

    @PostMapping(params = {"startDate", "endDate"})
    public Report createNewReportByPeriod(@RequestBody ReportCreationDto reportCreationDto,
                                          @RequestParam(required = false) UUID employeeId,
                                          @RequestParam(required = false) UUID assignerId,
                                          @RequestParam String startDate,
                                          @RequestParam String endDate) {
        return service.createNewReportByDate(
                reportCreationDto,
                employeeId,
                assignerId,
                startDate,
                endDate
        );
    }

    @GetMapping("/{id}")
    public Report getReportById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @GetMapping
    public List<Report> getAllReports() {
        return service.getAllReports();
    }
}
