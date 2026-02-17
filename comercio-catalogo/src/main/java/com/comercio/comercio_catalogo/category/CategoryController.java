package com.comercio.comercio_catalogo.category;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comercio.comercio_catalogo.category.dto.request.CategoryRequestDTO;
import com.comercio.comercio_catalogo.category.dto.response.CategoryResponseDTO;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<CategoryResponseDTO> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO getById(@PathVariable String id) {
        return categoryService.getById(id);
    }

    @PostMapping
    public CategoryResponseDTO create(@Valid @RequestBody CategoryRequestDTO categoryDTO) {
        return categoryService.create(categoryDTO);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        categoryService.delete(id);
    }
}
