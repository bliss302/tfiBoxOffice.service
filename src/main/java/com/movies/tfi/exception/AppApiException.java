package com.movies.tfi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppApiException extends Exception{
    private HttpStatus status;
    private String message;

    public AppApiException(HttpStatus status, String message){
        this.status = status;
        this.message = message;
    }

    public AppApiException(String message, HttpStatus status, String message1){
        super(message);
        this.status = status;
        this.message = message;
    }
}
