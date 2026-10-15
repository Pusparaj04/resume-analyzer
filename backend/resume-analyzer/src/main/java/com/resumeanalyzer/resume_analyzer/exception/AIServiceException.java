package com.resumeanalyzer.resume_analyzer.exception;

public class AIServiceException extends RuntimeException{
    public AIServiceException(String message){
        super(message);
    }

//    it is useful when gemini throws an underlying exception
    public AIServiceException(String message, Throwable cause){
        super(message, cause);
    }

}
