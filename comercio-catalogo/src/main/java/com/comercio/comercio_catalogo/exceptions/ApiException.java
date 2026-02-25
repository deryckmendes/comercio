package com.comercio.comercio_catalogo.exceptions;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ApiException(HttpStatus httpStatus, String message) {
        super(message != null && !message.isBlank() ? message : "Erro inesperado no servidor");
        this.httpStatus = httpStatus;
    }

    public HttpStatus getStatus() {
        return httpStatus;
    }
}
