package com.comercio.comercio_catalogo.category.dto.mapper;

import org.springframework.stereotype.Service;

import com.comercio.comercio_catalogo.category.Category;
import com.comercio.comercio_catalogo.category.dto.request.CategoryRequestDTO;
import com.comercio.comercio_catalogo.category.dto.response.CategoryResponseDTO;

@Service
public class CategoryDTOMapper {

    public CategoryResponseDTO toResponse(Category category) {
        return new CategoryResponseDTO(category.getId(), category.getName());
    }

    public Category toRequest(CategoryRequestDTO categoryDTO) {
        return new Category(null, categoryDTO.name());
    }
}
