package com.gmail.dlwk0807.dagotit.core.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException() {
        super();
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
