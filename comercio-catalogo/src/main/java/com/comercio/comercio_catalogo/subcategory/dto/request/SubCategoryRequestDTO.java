package com.comercio.comercio_catalogo.subcategory.dto.request;

import java.util.Set;

import com.comercio.comercio_catalogo.product.dto.ProductRequestDTO;

public record SubCategoryRequestDTO(
        String categoryId,
        String name,
        Set<ProductRequestDTO> products) {
}
