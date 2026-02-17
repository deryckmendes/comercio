package com.comercio.comercio_catalogo.category.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryRequestDTO(
        @NotBlank(message = "Category name is required")
        @Size(max = 15, message = "Category name must be at most 15 characters")
        String name) {
}
