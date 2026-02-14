package com.comercio.comercio_catalogo.subcategory;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @GetMapping
    public List<SubCategory> getAll() {
        return subCategoryService.getAll();
    }

    @GetMapping("/{id}")
    public SubCategory getById(@PathVariable String id) {
        return subCategoryService.getById(id);
    }

    @PostMapping
    public SubCategory create(SubCategory subCategory) {
        return subCategoryService.create(subCategory);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        subCategoryService.delete(id);
    }
}
