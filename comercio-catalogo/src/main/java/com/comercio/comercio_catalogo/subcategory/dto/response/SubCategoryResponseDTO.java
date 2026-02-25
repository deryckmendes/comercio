package com.comercio.comercio_catalogo.subcategory.dto.response;

import java.util.Set;

import com.comercio.comercio_catalogo.product.dto.ProductResponseDTO;

public record SubCategoryResponseDTO(
        String id,
        String categoryId,
        String name,
        Set<ProductResponseDTO> products) {

}
