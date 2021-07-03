package com.blablaprincess.springboot_simplejava.business.common.exceptions;

public class EmptyArrayException extends RuntimeException {

    public EmptyArrayException() {
        super("Expected array should have been not empty");
    }

    public EmptyArrayException(String message) {
        super(message);
    }

}
