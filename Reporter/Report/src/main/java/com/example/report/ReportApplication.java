package com.example.report;

import com.example.report.config.ReportProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ReportProperties.class)
@ConfigurationPropertiesScan("com.example.report.config")
@EntityScan({"com.example.taskmanager.entity", "com.example.report.entity"})
public class ReportApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReportApplication.class, args);
	}
}
