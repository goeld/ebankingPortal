package com.mycompany.api.ebankingPortal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class EBankingGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex, WebRequest request) {
        ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Invalid Request", ex.getMessage());
        return new ResponseEntity<ApiError>(error, HttpStatus.BAD_REQUEST);
    }
}
