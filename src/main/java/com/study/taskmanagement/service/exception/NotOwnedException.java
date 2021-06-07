package com.study.taskmanagement.service.exception;

public class NotOwnedException
        extends BusinessLayerException {

    public NotOwnedException(String... messageCodes) {
        super(messageCodes);
    }

    public NotOwnedException(Throwable cause, String... messageCodes) {
        super(cause, messageCodes);
    }

}
