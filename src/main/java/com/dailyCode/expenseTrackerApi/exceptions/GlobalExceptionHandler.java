package com.dailyCode.expenseTrackerApi.exceptions;

import com.dailyCode.expenseTrackerApi.entity.ErrorObjects;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ErrorObjects> handleExpenseNotFoundException(ResourceNotFoundException ex, WebRequest request){

        ErrorObjects errorObject = new ErrorObjects();
        errorObject.setStatusCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObjects>(errorObject, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorObjects> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex, WebRequest request){
        ErrorObjects errorObject  = new ErrorObjects();
        errorObject.setStatusCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObjects>(errorObject, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorObjects> handleGeneralException(Exception ex, WebRequest request){
        ErrorObjects errorObject = new ErrorObjects();
        errorObject.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimestamp(new Date());

        return new ResponseEntity<ErrorObjects>(errorObject, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ItemAlreadyExistException.class)
    public ResponseEntity<ErrorObjects> handleItermAlreadyExistException(ItemAlreadyExistException ex, WebRequest request){
        ErrorObjects errorObjects = new ErrorObjects();
        errorObjects.setStatusCode(HttpStatus.CONFLICT.value());
        errorObjects.setMessage(ex.getMessage());
        errorObjects.setTimestamp(new Date());

        return new ResponseEntity<ErrorObjects>(errorObjects, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("Timestamp", new Date());
        body.put("statusCode", HttpStatus.BAD_REQUEST.value());

        List<String> error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("message", error);

        return new ResponseEntity<Object>(body, HttpStatus.BAD_REQUEST);
    }
}
