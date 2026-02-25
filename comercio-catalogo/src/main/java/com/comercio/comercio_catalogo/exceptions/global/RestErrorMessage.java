package com.comercio.comercio_catalogo.exceptions.global;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

public class RestErrorMessage {
    private HttpStatus status;
    private String message;
    private String path;
    private LocalDateTime timestamp;

    public RestErrorMessage(HttpStatus status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = LocalDateTime.now();
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

}
