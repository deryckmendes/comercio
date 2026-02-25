package com.comercio.comercio_catalogo.rabbitmq;

public class Categoria {

    private String message;
    private Integer number;

    public Categoria() {
    }

    public Categoria(String message, Integer number) {
        this.message = message;
        this.number = number;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    // @Override
    // public String toString() {
    // return String.format("Categoria{message='%s'}", message);
    // }

}
