package com.study.taskmanagement.service.exception;

import org.springframework.context.MessageSourceResolvable;

public class NotFoundException
        extends BusinessLayerException
        implements MessageSourceResolvable {

    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String[] getCodes() {
        return new String[]{"application.business.notFound"};
    }
}
