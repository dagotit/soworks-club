package com.gmail.dlwk0807.dagachi.core.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException() {
        super();
    }

    public EmailNotFoundException(String message) {
        super(message);
    }
}
