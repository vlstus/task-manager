package com.study.taskmanagement.service.exception;

public class BusinessLayerException
        extends RuntimeException {

    public BusinessLayerException() {
    }

    public BusinessLayerException(String message) {
        super(message);
    }

    public BusinessLayerException(String message, Throwable cause) {
        super(message, cause);
    }

}
