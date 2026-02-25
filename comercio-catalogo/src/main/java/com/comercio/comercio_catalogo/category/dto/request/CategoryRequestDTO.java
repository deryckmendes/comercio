package com.comercio.comercio_catalogo.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        @NotBlank(message = "O nome da Categoria é necessário.") 
        @Size(max = 30, message = "O nome da Categoria deve ter no máximo 30 caracteres.") 
        String name) {
}
