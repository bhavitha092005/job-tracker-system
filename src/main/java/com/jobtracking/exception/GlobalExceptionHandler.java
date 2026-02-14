package com.jobtracking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //  NOT FOUND 
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiError(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
    }

    //  BAD REQUEST 
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> handleBadRequest(BadRequestException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiError(ex.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }

    //  UNAUTHORIZED ACTION 
    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ApiError> handleUnauthorizedAction(
            UnauthorizedActionException ex) {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiError(ex.getMessage(), HttpStatus.FORBIDDEN.value()));
    }

    // FALLBACK 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex) {

        ex.printStackTrace(); 

        ApiError error = new ApiError(
                "Internal server error",
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
