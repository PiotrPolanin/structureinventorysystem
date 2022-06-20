package com.company.structureinventorysystem.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final String ENTITY_NOT_FOUND_MESSAGE = "Entity was not found";

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity entityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return new ResponseEntity(ENTITY_NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND);
    }

}
