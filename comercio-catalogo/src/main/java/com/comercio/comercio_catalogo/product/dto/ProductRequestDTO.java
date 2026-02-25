package com.comercio.comercio_catalogo.product.dto;

public record ProductRequestDTO(
    String name,
    String description,
    double price,
    Integer quantity
) {
    
}
