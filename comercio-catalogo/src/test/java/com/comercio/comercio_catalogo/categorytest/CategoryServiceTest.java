package com.comercio.comercio_catalogo.categorytest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
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

import com.comercio.comercio_catalogo.category.Category;
import com.comercio.comercio_catalogo.category.CategoryRepository;
import com.comercio.comercio_catalogo.category.CategoryService;
import com.comercio.comercio_catalogo.category.dto.mapper.CategoryDTOMapper;
import com.comercio.comercio_catalogo.category.dto.request.CategoryRequestDTO;
import com.comercio.comercio_catalogo.category.dto.response.CategoryResponseDTO;

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
    void shouldCreateCategory() {
        CategoryRequestDTO requestDTO = new CategoryRequestDTO("Eletrônicos");
        Category category = new Category(null, "Eletrônicos");
        Category savedCategory = new Category("123", "Eletrônicos");
        CategoryResponseDTO responseDTO = new CategoryResponseDTO("123", "Eletrônicos");

        when(categoryDTOMapper.toRequest(requestDTO)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(savedCategory);
        when(categoryDTOMapper.toResponse(savedCategory)).thenReturn(responseDTO);

        CategoryResponseDTO result = categoryService.create(requestDTO);

        assertNotNull(result);
        assertEquals("123", result.id());
        assertEquals("Eletrônicos", result.name());

        verify(categoryDTOMapper).toRequest(requestDTO);
        verify(categoryDTOMapper).toResponse(savedCategory);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void shouldReturnAllCategories() {
        Category category1 = new Category("123", "Eletrônicos");
        Category category2 = new Category("124", "Roupas");
        CategoryResponseDTO responseDTO1 = new CategoryResponseDTO("123", "Eletrônicos");
        CategoryResponseDTO responseDTO2 = new CategoryResponseDTO("124", "Roupas");

        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));
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
    Category category = new Category("123", "Eletrônicos");
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
    when(categoryRepository.findById("123")).thenReturn(Optional.empty());

    assertThrows(IllegalArgumentException.class, () -> categoryService.getById("123"));
    // CategoryResponseDTO result = categoryService.getById("123");

    // assertNull(result);
    verify(categoryRepository, times(1)).findById("123");
    }

    // @Test
    // void shouldCreateCategoryee() {
    // // Category category = new Category(null, "Eletrônicos");
    // // Category savedCategory = new Category("123", "Eletrônicos");
    // Category category = new Category();
    // category.setId(null);
    // category.setName("Eletrônicos");

    // Category savedCategory = new Category();
    // savedCategory.setId("123");
    // savedCategory.setName("Eletrônicos");

    // when(categoryRepository.save(category)).thenReturn(savedCategory);

    // Category result = categoryService.create(category);
    // assertNotNull(result);
    // assertEquals("123", result.getId());
    // assertEquals("Eletrônicos", result.getName());

    // verify(categoryRepository, times(1)).save(category);
    // }

    @Test
    void shouldDeleteCategoryById() {
        doNothing().when(categoryRepository).deleteById("123");

        categoryService.delete("123");

        verify(categoryRepository, times(1)).deleteById("123");
    }
}
