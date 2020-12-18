package com.example.report.config;

import com.sun.istack.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "report")
@Validated
@Data
public class ReportProperties {
    @NotNull
    private String targetPort;
}
