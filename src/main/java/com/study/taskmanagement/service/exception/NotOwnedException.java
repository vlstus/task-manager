package com.study.taskmanagement.service.exception;

public class NotOwnedException extends BusinessLayerException {

    public NotOwnedException() {
    }

    public NotOwnedException(String message) {
        super(message);
    }

    public NotOwnedException(String message, Throwable cause) {
        super(message, cause);
    }

}
