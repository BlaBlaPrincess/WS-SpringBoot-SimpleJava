package com.blablaprincess.springboot_simplejava.business.validation;

public interface Validator<T> {
    void validate(T value) throws ValidationException;
}
