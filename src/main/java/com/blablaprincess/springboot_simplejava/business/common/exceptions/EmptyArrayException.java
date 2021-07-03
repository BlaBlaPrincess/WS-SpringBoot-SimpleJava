package com.blablaprincess.springboot_simplejava.business.common.exceptions;

import com.blablaprincess.springboot_simplejava.business.BusinessException;

public class EmptyArrayException extends BusinessException {

    public EmptyArrayException() {
        super("Expected array should have been not empty");
    }

    public EmptyArrayException(String message) {
        super(message);
    }

}
