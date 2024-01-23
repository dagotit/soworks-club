package com.gmail.dlwk0807.dagachi.core.exception;

public class DuplicationEmailSenderException extends RuntimeException{
    public DuplicationEmailSenderException() {
        super();
    }

    public DuplicationEmailSenderException(String message) {
        super(message);
    }
}
