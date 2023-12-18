package com.gmail.dlwk0807.dagotit.core.exception;

public class DuplicationEmailSenderException extends RuntimeException{
    public DuplicationEmailSenderException() {
        super();
    }

    public DuplicationEmailSenderException(String message) {
        super(message);
    }
}
