package com.blablaprincess.springboot_simplejava.rest;

import com.blablaprincess.springboot_simplejava.business.BusinessException;
import com.blablaprincess.springboot_simplejava.rest.dto.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(value = { BusinessException.class })
    public ResponseEntity<Object> handleBusinessLayerException(BusinessException exception) {
        return new ResponseEntity<Object>(
                new ErrorDto(exception.getMessage()), exception.getHttpStatus()
        );
    }

}
