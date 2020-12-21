package com.example.taskmanager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DateFormatException extends RuntimeException {
    public DateFormatException() {
        super("Date processing exception");
    }

    public DateFormatException(String date) {
        super("Cannot parse date " + date);
    }
}
