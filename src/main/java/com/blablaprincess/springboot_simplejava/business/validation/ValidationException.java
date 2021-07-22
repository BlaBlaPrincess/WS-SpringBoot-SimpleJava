package com.blablaprincess.springboot_simplejava.business.validation;

import com.blablaprincess.springboot_simplejava.business.BusinessException;

public class ValidationException extends BusinessException {

    public ValidationException() {
        super("Validation error");
    }

    public ValidationException(String message) {
        super(message);
    }

}
