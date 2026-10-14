package com.resumeanalyzer.resume_analyzer.exception;

public class InvalidFileException extends RuntimeException{
    public InvalidFileException(String message){
        super(message);
    }
}
