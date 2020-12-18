package com.example.report.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ServiceConnectionRefusedException extends RuntimeException {
    public ServiceConnectionRefusedException() {
        super("Cannot get response from task manager");
    }

    public ServiceConnectionRefusedException(String path) {
        super("Cannot get response from task manager, path:" + path);
    }
}
