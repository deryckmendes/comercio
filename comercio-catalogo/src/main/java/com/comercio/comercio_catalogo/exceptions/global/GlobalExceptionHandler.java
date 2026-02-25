package com.comercio.comercio_catalogo.exceptions.global;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.comercio.comercio_catalogo.exceptions.ApiException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<RestErrorMessage> ApiException(ApiException exception, HttpServletRequest request) {
        RestErrorMessage error = new RestErrorMessage(
                exception.getStatus(),
                exception.getMessage(),
                request.getRequestURI());
        {
            return ResponseEntity.status(exception.getStatus()).body(error);
        }
    }
}
