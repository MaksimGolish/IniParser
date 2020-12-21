package com.example.taskdriver.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class TaskLogDto {
    private UUID id;
    private String message;
    private Instant time;
}
