package com.example.project_chatgbt.exceptions;

public class BadDataException extends RuntimeException {
    public BadDataException(String message) {
        super(message);
    }
}
