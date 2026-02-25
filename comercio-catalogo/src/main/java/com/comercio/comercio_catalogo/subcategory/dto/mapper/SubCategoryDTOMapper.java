package com.comercio.comercio_catalogo.subcategory.dto.mapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comercio.comercio_catalogo.product.Product;
import com.comercio.comercio_catalogo.product.dto.ProductResponseDTO;
import com.comercio.comercio_catalogo.product.dto.mapper.ProductDTOMapper;
import com.comercio.comercio_catalogo.subcategory.SubCategory;
import com.comercio.comercio_catalogo.subcategory.dto.request.SubCategoryRequestDTO;
import com.comercio.comercio_catalogo.subcategory.dto.response.SubCategoryResponseDTO;

@Service
public class SubCategoryDTOMapper {

    @Autowired
    private ProductDTOMapper productDTOMapper;

    public SubCategoryResponseDTO toResponse(SubCategory subCategory) {
        Set<ProductResponseDTO> productDTOs = subCategory.getProducts().stream()
                .map(productDTOMapper::toResponse)
                .collect(Collectors.toSet());

        return new SubCategoryResponseDTO(subCategory.getId(), subCategory.getCategoryId(), subCategory.getName(),
                productDTOs);
    }

    public SubCategory toRequest(SubCategoryRequestDTO subCategoryDTO) {
        Set<Product> products = Optional.ofNullable(subCategoryDTO.products())
                .orElse(Set.of())
                .stream()
                .map(productDTOMapper::toRequest)
                .collect(Collectors.toSet());
        return new SubCategory(null, subCategoryDTO.categoryId(), subCategoryDTO.name(), products);
    }
}
