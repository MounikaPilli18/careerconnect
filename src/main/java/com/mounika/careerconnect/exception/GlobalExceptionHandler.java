package com.mounika.careerconnect.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mounika.careerconnect.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.MethodArgumentNotValidException;
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(JobNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiResponse<String> handleJobNotFound(JobNotFoundException ex) {

	    return new ApiResponse<>(
	            ex.getMessage(),
	            HttpStatus.NOT_FOUND.value(),
	            null
	    );
	}
    @ExceptionHandler(InvalidSortFieldException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleInvalidSortField(InvalidSortFieldException ex) {

        return new ApiResponse<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleValidationException(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Invalid job data");

        return new ApiResponse<>(
                message,
                HttpStatus.BAD_REQUEST.value(),
                null
        );
    }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiResponse<String> handleIllegalArgumentException(
            IllegalArgumentException ex) {

        return new ApiResponse<>(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                null
        );
    }
}