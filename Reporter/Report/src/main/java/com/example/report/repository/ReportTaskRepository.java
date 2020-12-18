package com.example.report.repository;

import com.example.report.entity.ReportTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReportTaskRepository extends JpaRepository<ReportTask, UUID> {
}
