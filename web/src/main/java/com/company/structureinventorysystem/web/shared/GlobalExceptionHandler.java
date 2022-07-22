package com.company.structureinventorysystem.web.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity entityNotFoundException(EntityNotFoundException exception) {
        return new ResponseEntity(exception.getLocalizedMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity httpMessageNotReadableException(HttpMessageNotReadableException exception) {
        return new ResponseEntity(String.format("Error to parse attribute %s", extract(exception.getLocalizedMessage())), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalStateException.class)
    public ResponseEntity illegalStateException(IllegalStateException exception) {
        return new ResponseEntity(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(IllegalArgumentException exception) {
        return new ResponseEntity(exception.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
    }

    private String extract(String charSequence) {
        Pattern attributePattern = Pattern.compile(".*\\[\"(.+)\"\\].*");
        Matcher matcher = attributePattern.matcher(charSequence.replace("\n", ""));
        return matcher.matches() ? matcher.group(1) : "";
    }

}
