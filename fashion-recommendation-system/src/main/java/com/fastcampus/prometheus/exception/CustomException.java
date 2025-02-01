package com.fastcampus.prometheus.exception;


import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    public final Map<String, String> validation = new HashMap<>();

    public abstract int getStatusCode();

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }

    public void addValidation(String fieldName, String message){
        validation.put(fieldName, message);
    }
}
