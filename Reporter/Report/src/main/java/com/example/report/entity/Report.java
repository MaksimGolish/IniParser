package com.example.report.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReportTask> opened;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReportTask> activated;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ReportTask> resolved;

    public Report(String name) {
        this.name = name;
    }
}
