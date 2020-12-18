package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SprintNotFoundException extends RuntimeException {
    public SprintNotFoundException() {
        super("Sprint not found");
    }

    public SprintNotFoundException(UUID id) {
        super("Sprint " + id + " not found");
    }
}
