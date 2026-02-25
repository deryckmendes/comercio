package com.comercio.comercio_catalogo.product.dto.mapper;

import org.springframework.stereotype.Service;

import com.comercio.comercio_catalogo.product.Product;
import com.comercio.comercio_catalogo.product.dto.ProductRequestDTO;
import com.comercio.comercio_catalogo.product.dto.ProductResponseDTO;

@Service
public class ProductDTOMapper {

    public ProductResponseDTO toResponse(Product product) {
        return new ProductResponseDTO(product.getId(), product.getName());
    }

    public Product toRequest(ProductRequestDTO productDTO) {
        return new Product(
                productDTO.name(),
                productDTO.description(),
                productDTO.price(),
                productDTO.quantity());
    }
}
