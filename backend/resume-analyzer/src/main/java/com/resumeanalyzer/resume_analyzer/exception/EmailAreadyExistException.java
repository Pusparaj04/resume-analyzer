package com.resumeanalyzer.resume_analyzer.exception;

public class EmailAreadyExistException extends RuntimeException {
    public EmailAreadyExistException(String message) {
        super(message);
    }
}
