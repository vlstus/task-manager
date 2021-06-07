package com.study.taskmanagement.controller.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public final class ErrorInfo {

    private final String[] messages;
    private final HttpStatus errorCode;

    public ErrorInfo(HttpStatus status, String... messages) {
        this.errorCode = status;
        this.messages = messages;
    }

}
