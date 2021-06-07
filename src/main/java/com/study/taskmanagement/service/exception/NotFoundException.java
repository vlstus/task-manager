package com.study.taskmanagement.service.exception;

public class NotFoundException
        extends BusinessLayerException {

    public NotFoundException(String... messageCodes) {
        super(messageCodes);
    }

    public NotFoundException(Throwable cause, String... messageCodes) {
        super(cause, messageCodes);
    }
}
