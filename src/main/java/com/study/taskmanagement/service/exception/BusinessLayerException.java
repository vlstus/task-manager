package com.study.taskmanagement.service.exception;

import org.springframework.context.MessageSourceResolvable;

public class BusinessLayerException
        extends RuntimeException
        implements MessageSourceResolvable {

    private final String[] messageCodes;

    public BusinessLayerException(String... messageCodes) {
        this.messageCodes = messageCodes;
    }

    public BusinessLayerException(Throwable cause, String... messageCodes) {
        super(cause);
        this.messageCodes = messageCodes;
    }

    @Override
    public String[] getCodes() {
        return messageCodes;
    }
}
