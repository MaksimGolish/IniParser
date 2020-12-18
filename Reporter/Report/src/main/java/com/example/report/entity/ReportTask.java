package com.example.report.entity;

import com.example.report.model.ReportTaskState;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
public class ReportTask {
    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String description;
    private ReportTaskState state;
    private UUID employee;
    private UUID assigner;
    private String opened;
    private String activated;
    private String resolved;

    @JsonCreator
    public ReportTask(@JsonProperty("id") UUID id,
                      @JsonProperty("name") String name,
                      @JsonProperty("description") String description,
                      @JsonProperty("state") ReportTaskState state,
                      @JsonProperty("employee") UUID employee,
                      @JsonProperty("assigner") UUID assigner,
                      @JsonProperty("opened") String opened,
                      @JsonProperty("activated") String activated,
                      @JsonProperty("resolved") String resolved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.state = state;
        this.employee = employee;
        this.assigner = assigner;
        this.opened = opened;
        this.activated = activated;
        this.resolved = resolved;
    }
}
