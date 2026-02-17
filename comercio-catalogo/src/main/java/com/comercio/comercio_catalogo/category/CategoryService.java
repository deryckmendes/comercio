package com.comercio.comercio_catalogo.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.comercio.comercio_catalogo.category.dto.mapper.CategoryDTOMapper;
import com.comercio.comercio_catalogo.category.dto.request.CategoryRequestDTO;
import com.comercio.comercio_catalogo.category.dto.response.CategoryResponseDTO;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryDTOMapper categoryDTOMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryDTOMapper = categoryDTOMapper;
    }

    public List<CategoryResponseDTO> getAll() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhuma categoria encontrada.");
        }

        return categories.stream()
                    .map(categoryDTOMapper::toResponse)
                    .toList();
    }

    public CategoryResponseDTO getById(String id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found with id: " + id));
        return categoryDTOMapper.toResponse(category);
    }

    public CategoryResponseDTO create(CategoryRequestDTO categoryDTO) {
        if (categoryRepository.existsByName(categoryDTO.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with name '" + categoryDTO.name() + "' already exists.");
        }
        Category category = categoryDTOMapper.toRequest(categoryDTO);
        Category saved = categoryRepository.save(category);
        return categoryDTOMapper.toResponse(saved);
    }

    public void delete(String id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
