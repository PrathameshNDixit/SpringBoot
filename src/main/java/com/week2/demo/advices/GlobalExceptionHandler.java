package com.week2.demo.advices;

import com.week2.demo.exceptions.ResourceNotFoundException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> handleResourceNotFoundException(ResourceNotFoundException e){
        ApiError error=ApiError.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .build();
        return buildErrorResponse(error);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e){
        ApiError error=ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(e.getMessage())
                .build();
        return buildErrorResponse(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleInvalidInputException(MethodArgumentNotValidException e){
        List<String> errors = e
                .getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());

        ApiError apiError=ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Input Validation Failed")
                .subErrors(errors)
                .build();

        return buildErrorResponse(apiError);
    }

    private ResponseEntity<ApiResponse<?>> buildErrorResponse(ApiError apiError) {
        return new ResponseEntity<>(new ApiResponse<>(apiError),apiError.getStatus());
    }
}
