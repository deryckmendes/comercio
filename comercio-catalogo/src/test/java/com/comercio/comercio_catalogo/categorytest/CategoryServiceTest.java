package com.comercio.comercio_catalogo.categorytest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import com.comercio.comercio_catalogo.category.Category;
import com.comercio.comercio_catalogo.category.CategoryRepository;
import com.comercio.comercio_catalogo.category.CategoryService;
import com.comercio.comercio_catalogo.category.dto.mapper.CategoryDTOMapper;
import com.comercio.comercio_catalogo.category.dto.request.CategoryRequestDTO;
import com.comercio.comercio_catalogo.category.dto.response.CategoryResponseDTO;
import com.comercio.comercio_catalogo.exceptions.ApiException;

public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryDTOMapper categoryDTOMapper;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should create category successfully")
    void shouldCreateCategoryCase1() {
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Eletrônicos");
        Category category = new Category(null, null, "Eletrônicos");
        when(categoryDTOMapper.toRequest(requestDTO)).thenReturn(category);

        Category savedCategory = new Category("123", "1", "Eletrônicos");
        when(categoryRepository.save(category)).thenReturn(savedCategory);

        CategoryResponseDTO responseDTO = new CategoryResponseDTO("123", "Eletrônicos");
        when(categoryDTOMapper.toResponse(savedCategory)).thenReturn(responseDTO);

        CategoryResponseDTO result = categoryService.create(requestDTO);

        assertNotNull(result);
        assertEquals("123", result.id());
        assertEquals("Eletrônicos", result.name());

        verify(categoryDTOMapper).toRequest(requestDTO);
        verify(categoryRepository, times(1)).save(category);
        verify(categoryDTOMapper).toResponse(savedCategory);
    }

    @Test
    @DisplayName("Should not create category when name already exists")
    void shouldCreateCategoryCase2() {
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Eletrônicos");
        when(categoryRepository.existsByName(requestDTO.name())).thenReturn(true);

        ApiException exception = assertThrows(ApiException.class, () -> {
            categoryService.create(requestDTO);
        });

        assertEquals(HttpStatus.CONFLICT, exception.getStatus());
        assertEquals("Categoria com o nome 'Eletrônicos' já existe.", exception.getMessage());

        verify(categoryRepository).existsByName("Eletrônicos");
        verify(categoryDTOMapper, never()).toRequest(any());
        verify(categoryRepository, never()).save(any());
        verify(categoryDTOMapper, never()).toResponse(any());

    }

    @Test
    @DisplayName("Should not create category when name is empty")
    void shouldCreateCategoryCase3() {
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("  ");

        ApiException exception = assertThrows(ApiException.class, () -> {
            categoryService.create(requestDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("O nome da categoria não pode estar vazio.", exception.getMessage());

        verify(categoryRepository, never()).existsByName(any());
        verify(categoryDTOMapper, never()).toRequest(any());
        verify(categoryRepository, never()).save(any());
        verify(categoryDTOMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("Should not create category when name is null")
    void shouldCreateCategoryCase4() {
        CategoryRequestDTO requestDTO = new CategoryRequestDTO(null);

        ApiException exception = assertThrows(ApiException.class, () -> {
            categoryService.create(requestDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("O nome da categoria não pode estar vazio.", exception.getMessage());

        verify(categoryRepository, never()).existsByName(any());
        verify(categoryDTOMapper, never()).toRequest(any());
        verify(categoryRepository, never()).save(any());
        verify(categoryDTOMapper, never()).toResponse(any());

    }

    @Test
    void shouldReturnAllCategories() {
        Category category1 = new Category("123", "1", "Eletrônicos");
        Category category2 = new Category("124", "1", "Roupas");
        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        CategoryResponseDTO responseDTO1 = new CategoryResponseDTO("123", "Eletrônicos");
        CategoryResponseDTO responseDTO2 = new CategoryResponseDTO("124", "Roupas");
        when(categoryDTOMapper.toResponse(category1)).thenReturn(responseDTO1);
        when(categoryDTOMapper.toResponse(category2)).thenReturn(responseDTO2);

        List<CategoryResponseDTO> result = categoryService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());

        assertEquals("123", result.get(0).id());
        assertEquals("Eletrônicos", result.get(0).name());

        assertEquals("124", result.get(1).id());
        assertEquals("Roupas", result.get(1).name());

        verify(categoryRepository, times(1)).findAll();
        verify(categoryDTOMapper).toResponse(category1);
        verify(categoryDTOMapper).toResponse(category2);
    }

    @Test
    void shouldReturnCategoryById() {
        // Category category = new Category("123", "Eletrônicos");
        Category category = new Category("123", "", "Eletrônicos");
        CategoryResponseDTO responseDTO = new CategoryResponseDTO("123", "Eletrônicos");

        when(categoryRepository.findById("123")).thenReturn(Optional.of(category));
        when(categoryDTOMapper.toResponse(category)).thenReturn(responseDTO);

        CategoryResponseDTO result = categoryService.getById("123");

        assertNotNull(result);
        assertEquals("123", result.id());
        assertEquals("Eletrônicos", result.name());

        verify(categoryRepository, times(1)).findById("123");
        verify(categoryDTOMapper).toResponse(category);
    }

    @Test
    void shouldBeReturnEmpty() {
        String id = "123";

        when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        ApiException exception = assertThrows(ApiException.class, () -> {
            categoryService.getById(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Categoria não encontrada com id: " + id, exception.getMessage());
        // assertThrows(IllegalArgumentException.class, () ->
        // categoryService.getById("123"));
        // CategoryResponseDTO result = categoryService.getById("123");

        // assertNull(result);
        verify(categoryRepository, times(1)).findById("123");
        verify(categoryDTOMapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("Should delete category when ID exists")
    void shouldDeleteCategoryByIdCase1() {
        String id = "123";

        // doNothing().when(categoryRepository).deleteById(id);
        when(categoryRepository.existsById(id)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(id);

        categoryService.delete(id);

        verify(categoryRepository, times(1)).existsById(id);
        verify(categoryRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should not delete category when ID not exists")
    void shouldDeleteCategoryByIdCase2() {
        String id = "123";

        when(categoryRepository.existsById(id)).thenReturn(false);

        ApiException exception = assertThrows(ApiException.class, () -> {
            categoryService.delete(id);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Categoria inexistente, não foi possível deletar a Categoria com id: '" + id,
                exception.getMessage());

        verify(categoryRepository, times(1)).existsById(id);
        verify(categoryRepository, never()).deleteById(any());
    }
}
