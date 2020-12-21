package com.example.report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TaskDeserializationException extends RuntimeException {
    public TaskDeserializationException() {
        super("Task deserialization error");
    }

    public TaskDeserializationException(String json, Throwable cause) {
        super("Cannot deserialize json: " + json, cause);
    }
}
