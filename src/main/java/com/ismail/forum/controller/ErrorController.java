package com.ismail.forum.controller;

import com.ismail.forum.error.NotFoundException;
import com.ismail.forum.model.WebResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;


@RestControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public WebResponse<String> constraintViolationException(ConstraintViolationException constraintViolationException) {
        return new WebResponse<String>(
                HttpStatus.BAD_REQUEST.value(),
                "Error",
                constraintViolationException.getMessage()
        );
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public WebResponse<String> notFound(NotFoundException notFoundException){
        return new WebResponse<String>(
                HttpStatus.NOT_FOUND.value(),
                "Error",
                notFoundException.getMessage()
        );
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        Map<String, String> errors = new LinkedHashMap<>();

        for (final FieldError err : ex.getBindingResult().getFieldErrors()) {
            errors.put(err.getField(), err.getDefaultMessage());
        }

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }
}
