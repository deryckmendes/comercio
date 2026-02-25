package com.comercio.comercio_catalogo.category;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.comercio.comercio_catalogo.category.dto.mapper.CategoryDTOMapper;
import com.comercio.comercio_catalogo.category.dto.request.CategoryRequestDTO;
import com.comercio.comercio_catalogo.category.dto.response.CategoryResponseDTO;
import com.comercio.comercio_catalogo.exceptions.ApiException;
import com.mongodb.DuplicateKeyException;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryDTOMapper categoryDTOMapper;

    public CategoryService(
            CategoryRepository categoryRepository,
            CategoryDTOMapper categoryDTOMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryDTOMapper = categoryDTOMapper;
    }

    private String getAuthenticatedUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Usuário não autenticado.");
        }

        return (String) authentication.getPrincipal();
    }

    public List<CategoryResponseDTO> getAll() {
        String userId = getAuthenticatedUserId();

        List<Category> categories = categoryRepository.findAllByUserId(userId);
        return categories.stream()
                .map(categoryDTOMapper::toResponse)
                .toList();
    }

    public CategoryResponseDTO getById(String id) {
        String userId = getAuthenticatedUserId();

        Category category = categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "Categoria não encontrada com id: " + id));

        return categoryDTOMapper.toResponse(category);
    }

    public CategoryResponseDTO create(CategoryRequestDTO categoryDTO) {
        String userId = getAuthenticatedUserId();

        if (categoryDTO.name() == null || categoryDTO.name().trim().isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST,
                    "O nome da categoria não pode estar vazio.");

        }
        if (categoryRepository.existsByNameAndUserId(categoryDTO.name(), userId)) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "Categoria com o nome '" + categoryDTO.name() + "' já existe.");
        }

        Category category = categoryDTOMapper.toRequest(categoryDTO);
        category.setUserId(userId);

        try {
            Category save = categoryRepository.save(category);
            return categoryDTOMapper.toResponse(save);
        } catch (DuplicateKeyException exception) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "Categoria com o nome '" + categoryDTO.name() + "' já existe.");
        }
    }

    public CategoryResponseDTO createInitialCategory(String userId) {
        String categoryName = "Categoria Inicial";

        Category category = new Category(null, userId, categoryName);

        try {
            Category save = categoryRepository.save(category);
            return categoryDTOMapper.toResponse(save);
        } catch (DuplicateKeyException exception) {
            throw new ApiException(HttpStatus.CONFLICT,
                    "Categoria com o nome '" + categoryName + "' já existe.");
        }
    }

    public void delete(String id) {
        String userId = getAuthenticatedUserId();

        Category category = categoryRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND,
                        "Categoria inexistente, não foi possível deletar a Categoria com id: '" + id));

        categoryRepository.delete(category);
    }
}
